package woowacourse.movie.activity.ticket.contract.presenter

import woowacourse.movie.activity.ticket.contract.TicketActivityContract
import woowacourse.movie.dto.movie.BookingMovieUIModel

class TicketActivityPresenter(val view: TicketActivityContract.View) :
    TicketActivityContract.Presenter {
    override fun loadData(data: BookingMovieUIModel) {
        view.showTicketInfo(data.movieTitle, data.date, data.time)
        view.showTicketInfo(data.ticketCount, data.seats, data.theaterName)
        view.showTicketPrice(data.seats, data.date, data.time)
    }
}
