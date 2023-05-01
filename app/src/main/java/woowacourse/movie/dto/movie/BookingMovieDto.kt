package woowacourse.movie.dto.movie

import woowacourse.movie.dto.seat.SeatsDto
import woowacourse.movie.dto.ticket.TicketCountDto

data class BookingMovieDto(
    val movie: MovieDto,
    val date: MovieDateDto,
    val time: MovieTimeDto,
    val ticketCount: TicketCountDto,
    val seats: SeatsDto,
) : java.io.Serializable {

    companion object {
        val bookingMovie = BookingMovieDto(
            movie = MovieDto.movieData,
            date = MovieDateDto.movieDate,
            time = MovieTimeDto.movieTime,
            ticketCount = TicketCountDto(0),
            seats = SeatsDto(),
        )
    }
}
