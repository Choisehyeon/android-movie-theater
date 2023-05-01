package woowacourse.movie.dto.seat

import woowacourse.movie.R

data class SeatRowUIModel(val row: Char) : java.io.Serializable {

    fun getColor(): Int {
        return when (row) {
            'A', 'B' -> R.color.b_seat_color
            'C', 'D' -> R.color.s_seat_color
            else -> R.color.a_seat_color
        }
    }

    companion object {
        fun of(row: Int): SeatRowUIModel = SeatRowUIModel((row + 64).toChar())
    }
}
