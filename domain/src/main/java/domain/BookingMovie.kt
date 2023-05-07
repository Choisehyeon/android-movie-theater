package domain

import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime

data class BookingMovie(
    val movieTitle: String,
    val date: MovieDate,
    val time: MovieTime,
    val ticketCount: TicketCount,
    val seats: Seats,
    val theater: String
)
