package woowacourse.movie.dto.movie

import java.io.Serializable
import java.time.LocalDate

data class MovieDateDto(val date: LocalDate) : Serializable {
    companion object {
        val movieDate = MovieDateDto(LocalDate.now())
    }
}
