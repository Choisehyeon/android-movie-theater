package woowacourse.movie.mapper.movie

import domain.Theater
import woowacourse.movie.dto.movie.TheaterUIModel

fun TheaterUIModel.mapToDomain(): Theater {
    return Theater(this.name, this.screeningTime)
}

fun Theater.mapToUIModel(): TheaterUIModel {
    return TheaterUIModel(this.name, this.screeningTime)
}
