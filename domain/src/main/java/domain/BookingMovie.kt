package domain

import domain.movieinfo.Movie
import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime

data class BookingMovie(
    val movie: Movie,
    val date: MovieDate,
    val time: MovieTime,
    val ticketCount: TicketCount,
    val seats: Seats,
)
