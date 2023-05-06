package woowacourse.movie.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object ReservationDatabase {
    fun getDatabase(context: Context): SQLiteDatabase {
        val dbHelper = ReservationDBHelper(context)
        return dbHelper.writableDatabase
    }
}
