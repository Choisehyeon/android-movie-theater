package woowacourse.movie

import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.detail.contract.MovieDetailActivityContract
import woowacourse.movie.activity.detail.contract.presenter.MovieDetailActivityPresenter
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.movie.mapToUIModel
import java.time.LocalDate

class MovieDetailPresenterTest {
    private lateinit var presenter: MovieDetailActivityPresenter
    private lateinit var view: MovieDetailActivityContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieDetailActivityPresenter(view)
    }

    @Test
    fun `더하기 버튼을 누르면 값이 올라간다`() {
        val slot = slot<TicketCountUIModel>()
        every { view.setBookerNumber(capture(slot)) } answers { println("slot = ${slot.captured}") }

        presenter.increaseNum()

        assertEquals(slot.captured.numberOfPeople, 2)
        verify { view.setBookerNumber(slot.captured) }
    }

    @Test
    fun `빼기 버튼을 누르면 값이 내려간다`() {
        val slot = slot<TicketCountUIModel>()
        every { view.setBookerNumber(capture(slot)) } answers { println("slot = ${slot.captured}") }

        presenter.increaseNum()
        presenter.increaseNum()
        presenter.decreaseNum()
        presenter.decreaseNum()

        assertEquals(slot.captured.numberOfPeople, 1)
        verify { view.setBookerNumber(slot.captured) }
    }

    @Test
    fun `데이터가 잘 로드 되었는지 확인`() {
        val slot = slot<MovieUIModel>()
        every { view.setMovieData(capture(slot)) } answers { println("slot = ${slot.captured}") }

        presenter.loadMovieData(MovieUIModel.movieData)

        assertEquals(slot.captured, MovieUIModel.movieData)
        verify { view.setMovieData(slot.captured) }
    }

    @Test
    fun `dateSpinner가 position에 맞게 설정되었는지 확인`() {
        val slot = slot<Int>()
        every { view.setDateSpinnerPosition(capture(slot)) } answers { println("slot = ${slot.captured}") }

        presenter.loadDateSpinnerPosition(1)

        assertEquals(slot.captured, 1)
        verify { view.setDateSpinnerPosition(slot.captured) }
    }

    @Test
    fun `timeSpinner가 position에 맞게 설정되었는지 확인`() {
        val slot = slot<Int>()
        every { view.setTimeSpinnerPosition(capture(slot)) } answers { println("slot = ${slot.captured}") }

        presenter.loadTimeSpinnerPosition(1)

        assertEquals(slot.captured, 1)
        verify { view.setTimeSpinnerPosition(slot.captured) }
    }

    @Test
    fun `예약 버튼을 클릭하면 data를 잘 넘기는지 확인`() {
        val movieSlot = slot<MovieUIModel>()
        val dateSlot = slot<MovieDateUIModel>()
        val timeSlot = slot<MovieTimeUIModel>()
        val countSlot = slot<TicketCountUIModel>()
        val date = "2022-02-03"
        val time = "19:00"
        every {
            view.showSeatSelectPage(
                capture(movieSlot),
                capture(countSlot),
                capture(dateSlot),
                capture(timeSlot),
            )
        } answers { println("slot = ${timeSlot.captured}") }

        presenter.onBookBtnClick(MovieUIModel.movieData, date, time)

        assertEquals(movieSlot.captured, MovieUIModel.movieData)
        assertEquals(countSlot.captured.numberOfPeople, 1)
        assertEquals(dateSlot.captured, MovieDate.of(date).mapToUIModel())
        assertEquals(timeSlot.captured, MovieTime.of(time).mapToUIModel())
        verify {
            view.showSeatSelectPage(
                movieSlot.captured,
                countSlot.captured,
                dateSlot.captured,
                timeSlot.captured,
            )
        }
    }

    @Test
    fun `dateSpinner에 data가 잘 로드되는지 확인`() {
        val startDate = LocalDate.of(2024, 5, 1)
        val endDate = LocalDate.of(2024, 5, 3)
        val expected: List<String> = listOf("2024-05-01", "2024-05-02", "2024-05-03")
        val slot = slot<List<String>>()
        every { view.setDateSpinnerData(capture(slot)) } just Runs

        presenter.loadDateSpinnerData(startDate, endDate)

        assertEquals(expected, slot.captured)
        verify { view.setDateSpinnerData(slot.captured) }
    }

    @Test
    fun `timeSpinner에 data가 잘 로드되는지 확인`() {
        val selectedDate = MovieDate.of("2023-05-06").mapToUIModel()
        val expected: List<String> = listOf("09:00", "11:00", "13:00", "15:00", "17:00", "19:00", "21:00", "23:00")
        val slot = slot<List<String>>()
        every { view.setTimeSpinnerData(capture(slot)) } just Runs

        presenter.loadTimeSpinnerData(selectedDate)

        assertEquals(expected, slot.captured)
        verify { view.setTimeSpinnerData(slot.captured) }
    }
}
