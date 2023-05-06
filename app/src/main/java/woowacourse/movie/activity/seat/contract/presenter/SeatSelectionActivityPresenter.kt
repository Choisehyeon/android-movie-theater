package woowacourse.movie.activity.seat.contract.presenter

import domain.Seat
import domain.Seats
import woowacourse.movie.R
import woowacourse.movie.activity.seat.SeatSelectionActivity.Companion.SEATS_POSITION
import woowacourse.movie.activity.seat.contract.SeatSelectionActivityContract
import woowacourse.movie.database.ReservationRepository
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.seat.mapToDomain
import woowacourse.movie.mapper.seat.mapToUIModel
import java.time.LocalDateTime

class SeatSelectionActivityPresenter(
    val view: SeatSelectionActivityContract.View,
    private val count: TicketCountUIModel,
    val date: MovieDateUIModel,
    val time: MovieTimeUIModel,
    val movie: MovieUIModel,
    private val repository: ReservationRepository,
) : SeatSelectionActivityContract.Presenter {
    private var seats = Seats()

    override fun loadSeats() {
        view.setUpSeatsView(seats.mapToUIModel(), ::onSeatClick)
    }

    override fun loadMovieTitle(title: String) {
        view.setMovieTitle(title)
    }

    private fun onSeatClick(seat: SeatUIModel, row: Int, col: Int) {
        when {
            isPossibleSelect(seat.mapToDomain(), count.numberOfPeople) -> addSeat(
                seat,
                row,
                col,
            )
            isSeatCancelable(seat.mapToDomain()) -> removeSeat(seat, row, col)
            else -> {
                view.showSeatSelectionError()
                return
            }
        }
        updatePrice(true)
        setEnterBtnClickable()
    }

    private fun isPossibleSelect(seat: Seat, count: Int): Boolean {
        return !seats.containsSeat(seat) && seats.isPossibleSeatSize(count)
    }

    private fun isSeatCancelable(seat: Seat): Boolean {
        return seats.containsSeat(seat)
    }

    override fun addSeat(seat: SeatUIModel, row: Int, col: Int) {
        seats.add(seat.mapToDomain())
        view.selectSeat(row, col)
    }

    override fun removeSeat(seat: SeatUIModel, row: Int, col: Int) {
        seats.remove(seat.mapToDomain())
        view.unselectSeat(row, col)
    }

    override fun updatePrice(priceCheck: Boolean) {
        val ticketPrice = seats.caculateSeatPrice(LocalDateTime.of(date.date, time.time))
        if (priceCheck) view.setPrice(ticketPrice) else view.setPrice(0)
    }

    override fun setEnterBtnClickable() {
        if (isPossibleEnter(count.numberOfPeople)) {
            view.setEnterBtnColor(R.color.enter)
            view.setEnterBtnOnClickListener { showBookingDialog() }
        } else {
            view.setEnterBtnColor(R.color.not_enter)
            view.setEnterBtnOnClickListener(null)
        }
    }

    override fun showBookingDialog() {
        view.showBookingDialog()
    }

    override fun startTicketActivity() {
        val bookingMovie = BookingMovieUIModel(movie.title, date, time, count, seats.mapToUIModel())
        repository.insertReservation(bookingMovie)
        view.moveTicketActivity(bookingMovie)
    }

    override fun onSavedInstanceState(outState: MutableMap<String, Any>) {
        outState[SEATS_POSITION] = seats.mapToUIModel()
    }

    override fun onRestoreInstanceState(savedInstanceState: Map<String, Any>) {
        val seatsDto = savedInstanceState[SEATS_POSITION] as SeatsUIModel
        seats = seatsDto.mapToDomain()
    }

    private fun isPossibleEnter(count: Int): Boolean {
        return seats.checkSeatSizeMatch(count)
    }
}
