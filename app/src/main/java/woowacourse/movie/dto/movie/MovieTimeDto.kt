package woowacourse.movie.dto.movie

import java.io.Serializable
import java.time.LocalTime

data class MovieTimeDto(val time: LocalTime) : Serializable {
    companion object {
        val movieTime = MovieTimeDto(LocalTime.now())
    }
}
