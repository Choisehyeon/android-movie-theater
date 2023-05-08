package woowacourse.movie.activity.seat.contract.presenter

import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.seat.contract.SeatSelectionActivityContract
import woowacourse.movie.database.ReservationRepository
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.dto.seat.SeatColUIModel
import woowacourse.movie.dto.seat.SeatRowUIModel
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.movie.mapToUIModel
import woowacourse.movie.mapper.seat.mapToUIModel

internal class SeatSelectionActivityPresenterTest {
    private lateinit var presenter: SeatSelectionActivityPresenter
    private lateinit var view: SeatSelectionActivityContract.View
    private val repository = mockk<ReservationRepository>()

    @Before
    fun setUp() {
        view = mockk()
        val count = TicketCountUIModel()
        val date = MovieDate.of("2023-05-06").mapToUIModel()
        val time = MovieTime.of("19:00").mapToUIModel()
        val movie = MovieUIModel.movieData
        val theater = TheaterUIModel("선릉", mapOf())
        presenter =
            SeatSelectionActivityPresenter(view, count, date, time, movie, theater, repository)
    }

    @Test
    fun `좌석이 잘 로드되었는지 확인`() {
        val slot = slot<SeatsUIModel>()
        every { view.setUpSeatsView(capture(slot), any()) } just Runs

        presenter.loadSeats()

        verify { view.setUpSeatsView(slot.captured, any()) }
    }

    @Test
    fun `영화 제목이 잘 로드되었는지 확인`() {
        val slot = slot<String>()
        every { view.setMovieTitle(capture(slot)) } just Runs

        presenter.loadMovieTitle(MovieUIModel.movieData.title)

        assertEquals(MovieUIModel.movieData.title, slot.captured)
        verify { view.setMovieTitle(slot.captured) }
    }

    @Test
    fun `좌석을 추가할 수 있을 때 좌석을 추가하는지 확인`() {
        val slotRow = slot<Int>()
        val slotCol = slot<Int>()
        every { view.selectSeat(capture(slotRow), capture(slotCol)) } just Runs

        presenter.addSeat(fakeSeat, 1, 2)

        verify { view.selectSeat(slotRow.captured, slotCol.captured) }
    }

    @Test
    fun `좌석을 취소할 때 좌석이 제거되는지 확인`() {
        val slotRow = slot<Int>()
        val slotCol = slot<Int>()
        every { view.unselectSeat(capture(slotRow), capture(slotCol)) } just Runs

        presenter.removeSeat(fakeSeat, 1, 2)

        verify { view.unselectSeat(slotRow.captured, slotCol.captured) }
    }

    @Test
    fun `티켓 가격이 잘 로드되었는지 확인`() {
        val slotPrice = slot<Int>()
        every { view.setPrice(capture(slotPrice)) } just Runs

        presenter.updatePrice(true)

        verify { view.setPrice(slotPrice.captured) }
    }

    @Test
    fun `다이얼로그가 잘 동작하는지 확인`() {
        every { view.showBookingDialog() } just Runs

        presenter.showBookingDialog()

        verify { view.showBookingDialog() }
    }

    @Test
    fun `티켓Activity로 데이터가 잘 넘어가는지 확인`() {
        val slot = slot<BookingMovieUIModel>()
        every { repository.insertReservation(capture(slot)) } just Runs
        every { view.moveTicketActivity(capture(slot)) } just Runs

        presenter.startTicketActivity()
        verify { view.moveTicketActivity(slot.captured) }
    }

    companion object {
        val fakeSeat = SeatUIModel(
            row = SeatRowUIModel('A'),
            col = SeatColUIModel(2),
        )
    }
}
