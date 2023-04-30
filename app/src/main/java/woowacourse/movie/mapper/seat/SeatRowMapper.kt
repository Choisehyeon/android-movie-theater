package woowacourse.movie.mapper.seat

import domain.SeatRow
import woowacourse.movie.dto.seat.SeatRowDto

fun SeatRow.mapToUIModel(): SeatRowDto {
    return SeatRowDto.of(this.row)
}

fun SeatRowDto.mapToDomain(): SeatRow {
    return SeatRow(this.row.code - 64)
}
