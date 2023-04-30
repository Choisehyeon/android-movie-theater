package woowacourse.movie.dto

import woowacourse.movie.dto.movie.BookingMovieDto

object BookingHistoryDto : java.io.Serializable {

    private val bookingHistory = mutableListOf<BookingMovieDto>()

    fun add(movie: BookingMovieDto) {
        bookingHistory.add(movie)
    }

    fun getHistory(): List<BookingMovieDto> = bookingHistory.toList()
}
