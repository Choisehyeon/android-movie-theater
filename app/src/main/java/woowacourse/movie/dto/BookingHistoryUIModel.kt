package woowacourse.movie.dto

import woowacourse.movie.dto.movie.BookingMovieUIModel

object BookingHistoryUIModel : java.io.Serializable {

    private val bookingHistory = mutableListOf<BookingMovieUIModel>()

    fun add(movie: BookingMovieUIModel) {
        bookingHistory.add(movie)
    }

    fun getHistory(): List<BookingMovieUIModel> = bookingHistory.toList()
}
