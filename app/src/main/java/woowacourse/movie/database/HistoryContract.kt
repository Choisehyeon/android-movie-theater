package woowacourse.movie.database

import android.provider.BaseColumns

object HistoryContract : BaseColumns {
    const val TABLE_NAME = "booking_history"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_MOVIE_TITLE = "movie_title"
    const val TABLE_COLUMN_DATE = "select_date"
    const val TABLE_COLUMN_TIME = "select_time"
    const val TABLE_COLUMN_TICKET_COUNT = "ticket_count"
    const val TABLE_COLUMN_THEATER_NAME = "theater_name"
}
