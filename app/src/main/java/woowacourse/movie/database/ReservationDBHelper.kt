package woowacourse.movie.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_DATE
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_MOVIE_TITLE
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_TICKET_COUNT
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_TIME
import woowacourse.movie.database.SeatContract.TABLE_COLUMN_COL
import woowacourse.movie.database.SeatContract.TABLE_COLUMN_HISTORY_ID
import woowacourse.movie.database.SeatContract.TABLE_COLUMN_ID
import woowacourse.movie.database.SeatContract.TABLE_COLUMN_ROW
import woowacourse.movie.database.SeatContract.TABLE_NAME

class ReservationDBHelper(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(HISTORY_CREATE_QUERY)
        db?.execSQL(SEAT_CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(HISTORY_DROP_QUERY)
        db?.execSQL(SEAT_DROP_QUERY)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "lope_db"
        private const val HISTORY_CREATE_QUERY = "CREATE TABLE ${HistoryContract.TABLE_NAME} (" +
            "${HistoryContract.TABLE_COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$TABLE_COLUMN_MOVIE_TITLE TEXT," +
            "$TABLE_COLUMN_DATE TEXT," +
            "$TABLE_COLUMN_TIME TEXT," +
            "$TABLE_COLUMN_TICKET_COUNT TEXT)"
        private const val SEAT_CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
            "$TABLE_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$TABLE_COLUMN_HISTORY_ID INTEGER," +
            "$TABLE_COLUMN_ROW TEXT, " +
            "$TABLE_COLUMN_COL INTEGER NOT NULL)"

        private const val HISTORY_DROP_QUERY = "DROP TABLE IF EXISTS ${HistoryContract.TABLE_NAME}"
        private const val SEAT_DROP_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
