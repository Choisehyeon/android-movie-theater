package woowacourse.movie.mapper.seat

import domain.SeatCol
import woowacourse.movie.dto.seat.SeatColUIModel

fun SeatCol.mapToUIModel(): SeatColUIModel {
    return SeatColUIModel(this.col)
}

fun SeatColUIModel.mapToDomain(): SeatCol {
    return SeatCol(this.col)
}
