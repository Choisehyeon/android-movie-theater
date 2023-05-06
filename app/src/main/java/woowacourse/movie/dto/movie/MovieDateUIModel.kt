package woowacourse.movie.dto.movie

import java.io.Serializable
import java.time.LocalDate

data class MovieDateUIModel(val date: LocalDate) : Serializable {

    override fun toString(): String {
        return date.toString()
    }

    companion object {
        val movieDate = MovieDateUIModel(LocalDate.now())
    }
}
