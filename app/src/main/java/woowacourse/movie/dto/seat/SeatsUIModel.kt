package woowacourse.movie.dto.seat

import java.io.Serializable

data class SeatsUIModel(val seats: List<SeatUIModel> = emptyList()) : Serializable {
    fun getSeatsPositionToString(): String {
        return seats.map { it.getString() }.joinToString(", ")
    }
}
