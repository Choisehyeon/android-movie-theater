package woowacourse.movie.activity

import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel

interface TicketActivityContract {

    interface View {
        var presenter: Presenter
        fun showTicketInfo(movie: MovieUIModel, date: MovieDateUIModel, time: MovieTimeUIModel)
        fun showTicketInfo(ticket: TicketCountUIModel, seats: SeatsUIModel)
        fun showTicketPrice(seats: SeatsUIModel, date: MovieDateUIModel, time: MovieTimeUIModel)
    }

    interface Presenter {
        fun loadData(data: BookingMovieUIModel)
    }
}
