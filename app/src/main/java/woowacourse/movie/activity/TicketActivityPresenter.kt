package woowacourse.movie.activity

import woowacourse.movie.dto.movie.BookingMovieUIModel

class TicketActivityPresenter(val view: TicketActivityContract.View) :
    TicketActivityContract.Presenter {
    override fun loadData(data: BookingMovieUIModel) {
        view.showTicketInfo(data.movie, data.date, data.time)
        view.showTicketInfo(data.ticketCount, data.seats)
        view.showTicketPrice(data.seats, data.date, data.time)
    }
}
