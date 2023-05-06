package woowacourse.movie.dto.movie

import java.io.Serializable
import java.time.LocalTime

data class MovieTimeUIModel(val time: LocalTime) : Serializable {

    override fun toString(): String {
        return time.toString()
    }
    companion object {
        val movieTime = MovieTimeUIModel(LocalTime.now())
    }
}
