package woowacourse.movie.dto.seat

import java.io.Serializable

data class SeatUIModel(val row: SeatRowUIModel, val col: SeatColUIModel) : Serializable {
    fun getString(): String {
        return "${row.row}${col.col}"
    }
}
