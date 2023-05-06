package woowacourse.movie.activity

import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import java.time.LocalDate
import java.time.LocalTime

interface TicketActivityContract {

    interface View {
        val presenter: Presenter

        fun setToolbar()

        fun formatMovieDateTime(date: LocalDate, time: LocalTime): String
        fun showTicketInfo(title: String, date: MovieDateUIModel, time: MovieTimeUIModel)
        fun showTicketInfo(ticket: TicketCountUIModel, seats: SeatsUIModel)
        fun showTicketPrice(seats: SeatsUIModel, date: MovieDateUIModel, time: MovieTimeUIModel)
    }

    interface Presenter {
        fun loadData(data: BookingMovieUIModel)
    }
}
