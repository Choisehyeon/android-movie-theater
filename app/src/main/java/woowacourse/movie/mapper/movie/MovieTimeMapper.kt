package woowacourse.movie.mapper.movie

import domain.movieinfo.MovieTime
import woowacourse.movie.dto.movie.MovieTimeUIModel

fun MovieTimeUIModel.mapToDomain(): MovieTime {
    return MovieTime(this.time)
}

fun MovieTime.mapToUIModel(): MovieTimeUIModel {
    return MovieTimeUIModel(this.time)
}
