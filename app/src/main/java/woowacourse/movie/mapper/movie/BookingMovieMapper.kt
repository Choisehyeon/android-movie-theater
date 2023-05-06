package woowacourse.movie.mapper.movie

import domain.BookingMovie
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.mapper.seat.mapToDomain
import woowacourse.movie.mapper.seat.mapToUIModel
import woowacourse.movie.mapper.ticket.mapToDomain
import woowacourse.movie.mapper.ticket.mapToUIModel

fun BookingMovieUIModel.mapToDomain(): BookingMovie {
    return BookingMovie(this.movieTitle, this.date.mapToDomain(), this.time.mapToDomain(), this.ticketCount.mapToDomain(), this.seats.mapToDomain())
}

fun BookingMovie.mapToUIModel(): BookingMovieUIModel {
    return BookingMovieUIModel(this.movieTitle, this.date.mapToUIModel(), this.time.mapToUIModel(), this.ticketCount.mapToUIModel(), this.seats.mapToUIModel())
}
