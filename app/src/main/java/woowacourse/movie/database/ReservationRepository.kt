package woowacourse.movie.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_DATE
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_ID
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_MOVIE_TITLE
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_TICKET_COUNT
import woowacourse.movie.database.HistoryContract.TABLE_COLUMN_TIME
import woowacourse.movie.database.SeatContract.TABLE_COLUMN_COL
import woowacourse.movie.database.SeatContract.TABLE_COLUMN_HISTORY_ID
import woowacourse.movie.database.SeatContract.TABLE_COLUMN_ROW
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.seat.SeatColUIModel
import woowacourse.movie.dto.seat.SeatRowUIModel
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.movie.mapToUIModel

class ReservationRepository(val database: SQLiteDatabase) {

    fun insertReservation(item: BookingMovieUIModel) {
        val id: Long = insertHistory(item)
        insertSeats(id, item)
    }

    private fun insertHistory(item: BookingMovieUIModel): Long {
        val history = ContentValues().apply {
            put(TABLE_COLUMN_MOVIE_TITLE, item.movieTitle)
            put(TABLE_COLUMN_DATE, item.date.toString())
            put(TABLE_COLUMN_TIME, item.time.toString())
            put(TABLE_COLUMN_TICKET_COUNT, item.ticketCount.numberOfPeople)
        }
        return database.insert(HistoryContract.TABLE_NAME, null, history)
    }

    private fun insertSeats(id: Long, item: BookingMovieUIModel) {
        item.seats.seats.forEach {
            val seat = ContentValues().apply {
                put(TABLE_COLUMN_HISTORY_ID, id)
                put(TABLE_COLUMN_ROW, it.row.row.toString())
                put(TABLE_COLUMN_COL, it.col.col)
            }
            database.insert(SeatContract.TABLE_NAME, null, seat)
        }
    }

    private fun getHistoryAll(): Cursor {
        val query = "SELECT * FROM ${HistoryContract.TABLE_NAME}"
        return database.rawQuery(query, null)
    }

    fun getAll(): List<BookingMovieUIModel> {
        val histories = mutableListOf<BookingMovieUIModel>()
        getHistoryAll().use {
            while(it.moveToNext()) {
                histories.add(getHistory(it))
            }
        }
        return histories
    }

    @SuppressLint("Range")
    private fun getHistory(cursor: Cursor): BookingMovieUIModel {
        val movieTitle = cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_MOVIE_TITLE))
        val date = cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_DATE))
        val time = cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_TIME))
        val ticketCount = cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_TICKET_COUNT))
        val historyId = cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_ID))
        val seats = getSeats(historyId)
        return BookingMovieUIModel(
            movieTitle,
            MovieDate.of(date).mapToUIModel(),
            MovieTime.of(time).mapToUIModel(),
            TicketCountUIModel(ticketCount),
            seats,
        )
    }

    private fun getSeatsAll(historyId: Int): Cursor {
        val query =
            "SELECT * FROM ${SeatContract.TABLE_NAME} WHERE ${SeatContract.TABLE_COLUMN_HISTORY_ID} = $historyId"
        return database.rawQuery(query, null)
    }

    @SuppressLint("Range")
    private fun getSeats(historyId: Int): SeatsUIModel {
        val seats = mutableListOf<SeatUIModel>()
        getSeatsAll(historyId).use {
            while (it.moveToNext()) {
                val row = it.getString(it.getColumnIndex(TABLE_COLUMN_ROW))
                val col = it.getInt(it.getColumnIndex(TABLE_COLUMN_COL))
                seats.add(SeatUIModel(SeatRowUIModel(row.first()), SeatColUIModel(col)))
            }
        }
        return SeatsUIModel(seats)
    }
}
