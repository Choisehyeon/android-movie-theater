package woowacourse.movie.mapper.seat

import domain.Seat
import woowacourse.movie.dto.seat.SeatUIModel

fun SeatUIModel.mapToDomain(): Seat {
    return Seat(this.row.mapToDomain(), this.col.mapToDomain())
}

fun Seat.mapToUIModel(): SeatUIModel {
    return SeatUIModel(this.row.mapToUIModel(), this.col.mapToUIModel())
}
