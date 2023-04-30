package woowacourse.movie

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.activity.SeatSelectionActivity
import woowacourse.movie.dto.movie.BookingMovieDto
import java.time.LocalDateTime
import java.time.ZoneId

class MovieAlarmManager(val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun putAlarm(bookingMovie: BookingMovieDto) {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            setAlarmTime(bookingMovie),
            createReceiverPendingIntent(bookingMovie),
        )
    }

    private fun setAlarmTime(bookingMovie: BookingMovieDto): Long {
        val date = bookingMovie.date.date
        val time = bookingMovie.time.time

        val movieDateTime = LocalDateTime.of(date, time)
        val timeZone = ZoneId.of("Asia/Seoul")
        return movieDateTime.minusMinutes(30).atZone(timeZone).toInstant().toEpochMilli()
    }

    private fun createReceiverPendingIntent(bookingMovie: BookingMovieDto): PendingIntent {
        return Intent(context, AlarmReceiver::class.java).let {
            it.action = AlarmReceiver.ALARM_CODE
            it.putExtra(SeatSelectionActivity.BOOKING_MOVIE_KEY, bookingMovie)
            PendingIntent.getBroadcast(
                context,
                AlarmReceiver.REQUEST_CODE,
                it,
                PendingIntent.FLAG_IMMUTABLE,
            )
        }
    }
}
