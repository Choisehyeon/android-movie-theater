package woowacourse.movie.dto.movie

import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel

data class BookingMovieUIModel(
    val movieTitle: String,
    val date: MovieDateUIModel,
    val time: MovieTimeUIModel,
    val ticketCount: TicketCountUIModel,
    val seats: SeatsUIModel,
) : java.io.Serializable {
    companion object {
        val bookingMovie = BookingMovieUIModel(
            movieTitle = MovieUIModel.movieData.title,
            date = MovieDateUIModel.movieDate,
            time = MovieTimeUIModel.movieTime,
            ticketCount = TicketCountUIModel(0),
            seats = SeatsUIModel(),
        )
    }
}
