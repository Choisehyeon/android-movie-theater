package woowacourse.movie

import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.ticket.contract.TicketActivityContract
import woowacourse.movie.activity.ticket.contract.presenter.TicketActivityPresenter
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.seat.SeatColUIModel
import woowacourse.movie.dto.seat.SeatRowUIModel
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.movie.mapToUIModel

internal class TicketActivityPresenterTest {
    private lateinit var presenter: TicketActivityPresenter
    private lateinit var view: TicketActivityContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = TicketActivityPresenter(view)
    }

    @Test
    fun `티켓영화정보가 정확하게 보여지는지 확인`() {
        val slotTitle = slot<String>()
        val slotDate = slot<MovieDateUIModel>()
        val slotTime = slot<MovieTimeUIModel>()
        every {
            view.showTicketMovieInfo(
                capture(slotTitle),
                capture(slotDate),
                capture(slotTime),
            )
        } answers { println("slot = ${slotTitle.captured}") }
        every { view.showTicketInfo(any(), any(), any()) } just Runs
        every { view.showTicketPrice(any(), any(), any()) } just Runs

        presenter.loadData(fakeBookingMovie)

        assertEquals(fakeBookingMovie.movieTitle, slotTitle.captured)
        assertEquals(fakeBookingMovie.date, slotDate.captured)
        assertEquals(fakeBookingMovie.time, slotTime.captured)
    }

    @Test
    fun `티켓정보가 정확하게 보여지는지 확인`() {
        val slotCount = slot<TicketCountUIModel>()
        val slotSeats = slot<SeatsUIModel>()
        val slotTheaterName = slot<String>()
        every {
            view.showTicketInfo(
                capture(slotCount),
                capture(slotSeats),
                capture(slotTheaterName),
            )
        } answers { println("slot = ${slotCount.captured}") }
        every { view.showTicketMovieInfo(any(), any(), any()) } just Runs
        every { view.showTicketPrice(any(), any(), any()) } just Runs

        presenter.loadData(fakeBookingMovie)

        assertEquals(fakeBookingMovie.ticketCount, slotCount.captured)
        assertEquals(fakeBookingMovie.seats, slotSeats.captured)
        assertEquals(fakeBookingMovie.theaterName, slotTheaterName.captured)
    }

    @Test
    fun `티켓가격정보가 정확하게 보여지는지 확인`() {
        val slotSeats = slot<SeatsUIModel>()
        val slotDate = slot<MovieDateUIModel>()
        val slotTime = slot<MovieTimeUIModel>()
        every {
            view.showTicketPrice(
                capture(slotSeats),
                capture(slotDate),
                capture(slotTime),
            )
        } answers { println("slot = ${slotSeats.captured}") }
        every { view.showTicketInfo(any(), any(), any()) } just Runs
        every { view.showTicketMovieInfo(any(), any(), any()) } just Runs

        presenter.loadData(fakeBookingMovie)

        assertEquals(fakeBookingMovie.seats, slotSeats.captured)
        assertEquals(fakeBookingMovie.date, slotDate.captured)
        assertEquals(fakeBookingMovie.time, slotTime.captured)
    }

    companion object {
        val fakeBookingMovie = BookingMovieUIModel(
            movieTitle = "해리포터",
            date = MovieDate.of("2023-05-06").mapToUIModel(),
            time = MovieTime.of("13:00").mapToUIModel(),
            ticketCount = TicketCountUIModel(1),
            seats = SeatsUIModel(
                listOf(SeatUIModel(SeatRowUIModel('A'), SeatColUIModel(1))),
            ),
            theaterName = "선릉",
        )
    }
}
