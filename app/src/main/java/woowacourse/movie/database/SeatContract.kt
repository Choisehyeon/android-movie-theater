package woowacourse.movie.database

import android.provider.BaseColumns

object SeatContract : BaseColumns {
    const val TABLE_NAME = "seat"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_HISTORY_ID = "history_id"
    const val TABLE_COLUMN_ROW = "seat_row"
    const val TABLE_COLUMN_COL = "seat_col"
}
