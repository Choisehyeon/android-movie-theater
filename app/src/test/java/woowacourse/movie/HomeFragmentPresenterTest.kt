package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.database.TheaterRepository
import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.dto.movie.MovieDummy
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.fragment.home.contract.HomeFragmentContract
import woowacourse.movie.fragment.home.contract.presenter.HomeFragmentPresenter

internal class HomeFragmentPresenterTest {

    private lateinit var presenter: HomeFragmentPresenter
    private lateinit var view: HomeFragmentContract.View
    private val repository = mockk<TheaterRepository>()

    @Before
    fun setUp() {
        view = mockk()
        presenter = HomeFragmentPresenter(view, repository)
    }

    @Test
    fun `리사이클러뷰에 데이터가 잘 로드되는지 확인`() {
        val slotMovie = slot<List<MovieUIModel>>()
        val slotAd = slot<AdUIModel>()
        every { view.setRecyclerView(capture(slotMovie), capture(slotAd)) } just Runs

        presenter.loadDatas()

        assertEquals(slotMovie.captured, MovieDummy.movieDatas)
        assertEquals(slotAd.captured, AdUIModel.getAdData())
        verify { view.setRecyclerView(slotMovie.captured, slotAd.captured) }
    }

    @Test
    fun `영화를 클릭하면 Fragment에 정보가 잘 전달되는지 확인`() {
        val slot = slot<Int>()
        val slotMovie = slot<MovieUIModel>()
        val slotTheaters = slot<List<TheaterUIModel>>()
        every { repository.getTheaterByMovieId(capture(slot)) } returns listOf(TheaterUIModel.theater)
        every { view.showTheaterFragment(capture(slotMovie), capture(slotTheaters))} just Runs

        presenter.onMovieItemClick(MovieUIModel.movieData)

        assertEquals(slotMovie.captured, MovieUIModel.movieData)
        assertEquals(slotTheaters.captured, listOf(TheaterUIModel.theater))
        verify { view.showTheaterFragment(slotMovie.captured, slotTheaters.captured) }
    }

    @Test
    fun `광고를 클릭하면 광고 정보가 넘어가는지 확인`() {
        val slot = slot<AdUIModel>()
        every { view.showAd(capture(slot))} just Runs

        presenter.onAdItemClick(AdUIModel.getAdData())

        assertEquals(slot.captured, AdUIModel.getAdData())
        verify { view.showAd(slot.captured) }
    }
}
