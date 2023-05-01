package woowacourse.movie.dto.movie

import java.io.Serializable
import java.time.LocalDate

data class MovieDateUIModel(val date: LocalDate) : Serializable {
    companion object {
        val movieDate = MovieDateUIModel(LocalDate.now())
    }
}
