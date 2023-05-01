package woowacourse.movie.mapper.movie

import domain.movieinfo.Movie
import woowacourse.movie.dto.movie.MovieUIModel

fun MovieUIModel.mapToDomain(): Movie {
    return Movie(
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        runningTime = this.runningTime,
        description = this.description,
        moviePoster = this.moviePoster,
    )
}

fun Movie.mapToUIModel(): MovieUIModel {
    return MovieUIModel(
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        runningTime = this.runningTime,
        description = this.description,
        moviePoster = this.moviePoster,
    )
}
