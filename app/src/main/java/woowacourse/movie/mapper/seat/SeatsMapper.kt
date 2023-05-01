package woowacourse.movie.mapper.seat

import domain.Seat
import domain.Seats
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatsUIModel

fun SeatsUIModel.mapToDomain(): Seats {
    return Seats(this.seats.map { Seat(it.row.mapToDomain(), it.col.mapToDomain()) })
}

fun Seats.mapToUIModel(): SeatsUIModel {
    return SeatsUIModel(this.seats.map { SeatUIModel(it.row.mapToUIModel(), it.col.mapToUIModel()) })
}
