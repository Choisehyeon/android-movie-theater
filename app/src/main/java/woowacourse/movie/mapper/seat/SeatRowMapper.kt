package woowacourse.movie.mapper.seat

import domain.SeatRow
import woowacourse.movie.dto.seat.SeatRowUIModel

fun SeatRow.mapToUIModel(): SeatRowUIModel {
    return SeatRowUIModel.of(this.row)
}

fun SeatRowUIModel.mapToDomain(): SeatRow {
    return SeatRow(this.row.code - 64)
}
