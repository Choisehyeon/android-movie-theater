package woowacourse.movie.mapper.movie

import domain.movieinfo.MovieDate
import woowacourse.movie.dto.movie.MovieDateUIModel

fun MovieDateUIModel.mapToDomain(): MovieDate {
    return MovieDate(this.date)
}

fun MovieDate.mapToUIModel(): MovieDateUIModel {
    return MovieDateUIModel(this.date)
}
