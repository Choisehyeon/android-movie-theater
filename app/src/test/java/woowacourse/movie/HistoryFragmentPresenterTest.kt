package woowacourse.movie

import androidx.test.runner.screenshot.Screenshot.capture
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.database.ReservationRepository
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.fragment.history.contract.HistoryFragmentContract
import woowacourse.movie.fragment.history.contract.presenter.HistoryFragmentPresenter

internal class HistoryFragmentPresenterTest {
    private lateinit var presenter: HistoryFragmentPresenter
    private lateinit var view: HistoryFragmentContract.View
    private val repository = mockk<ReservationRepository>()

    @Before
    fun setUp() {
        view = mockk()
        presenter = HistoryFragmentPresenter(view, repository)
    }

    @Test
    fun `리사이클러뷰가 잘 만들어지는지 확인`() {
        every { view.setRecyclerView() } just Runs

        presenter.init()

        verify { view.setRecyclerView() }
    }

    @Test
    fun `데이터가 잘 로드되는지 확인`() {
        val slot = slot<List<BookingMovieUIModel>>()
        every { repository.getAll() } returns listOf(BookingMovieUIModel.bookingMovie)
        every { view.updateRecyclerView(capture(slot)) } just Runs

        presenter.loadDatas()

        verify { view.updateRecyclerView(slot.captured) }
    }

    @Test
    fun `버튼을 누르면 티켓 정보를 넘기는지 확인`() {
        val slot = slot<BookingMovieUIModel>()
        every { view.showMovieTicket(capture(slot))} just Runs

        presenter.onHistoryClick(BookingMovieUIModel.bookingMovie)

        assertEquals(slot.captured, BookingMovieUIModel.bookingMovie)
        verify { view.showMovieTicket(slot.captured) }
    }
}
