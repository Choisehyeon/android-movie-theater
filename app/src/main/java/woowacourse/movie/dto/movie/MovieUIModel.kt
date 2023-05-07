package woowacourse.movie.dto.movie

import androidx.annotation.DrawableRes
import woowacourse.movie.fragment.home.recyclerview.ViewType
import java.io.Serializable
import java.time.LocalDate

data class MovieUIModel(
    val viewType: ViewType = ViewType.MOVIE_VIEW,
    val id: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val description: String,
    @DrawableRes val moviePoster: Int,
) : Serializable {
    companion object {
        val movieData = MovieUIModel(
            viewType = ViewType.MOVIE_VIEW,
            id = 0,
            title = "",
            startDate = LocalDate.now(),
            endDate = LocalDate.now(),
            runningTime = 0,
            description = "",
            moviePoster = 0,
        )
    }
}
