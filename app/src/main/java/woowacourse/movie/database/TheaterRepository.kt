package woowacourse.movie.database

import woowacourse.movie.dto.movie.TheaterUIModel

class TheaterRepository(private val theaters: List<TheaterUIModel>) {

    fun getTheaterByMovieId(movieId: Int): List<TheaterUIModel> {
        return theaters.filter { it.screeningTime.containsKey(movieId) }
    }
}
