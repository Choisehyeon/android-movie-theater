package woowacourse.movie.activity.seat.contract

import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatsUIModel

interface SeatSelectionActivityContract {

    interface View {
        val presenter: Presenter
        fun setUpSeatsView(seats: SeatsUIModel, onSeatClick: (SeatUIModel, Int, Int) -> Unit)
        fun setMovieTitle(title: String)
        fun selectSeat(row: Int, col: Int)
        fun unselectSeat(row: Int, col: Int)
        fun showSeatSelectionError()
        fun setPrice(money: Int)
        fun setEnterBtnColor(color: Int)
        fun setEnterBtnOnClickListener(listener: (() -> Unit)?)
        fun showBookingDialog()
        fun moveTicketActivity(bookingMovie: BookingMovieUIModel)
    }

    interface Presenter {
        fun loadSeats()
        fun loadMovieTitle(title: String)
        fun addSeat(seat: SeatUIModel, row: Int, col: Int)
        fun removeSeat(seat: SeatUIModel, row: Int, col: Int)
        fun updatePrice(priceCheck: Boolean)
        fun setEnterBtnClickable()
        fun showBookingDialog()
        fun startTicketActivity()
        fun onSavedInstanceState(outState: MutableMap<String, Any>)
        fun onRestoreInstanceState(savedInstanceState : Map<String, Any>)
    }
}
