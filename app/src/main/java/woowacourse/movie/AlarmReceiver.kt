package woowacourse.movie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.activity.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.activity.TicketActivity
import woowacourse.movie.dto.movie.BookingMovieUIModel

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreference = SettingPreference(context)
        if (intent.action == ALARM_CODE && sharedPreference.loadData()) {
            NotificationManager.createNotificationChannel(context)

            val bookingMovie = intent.getSerializableExtra(BOOKING_MOVIE_KEY) as BookingMovieUIModel
            Log.d(BOOKING_MOVIE_KEY, bookingMovie.toString())

            NotificationManager.createNotificationBuilder(
                context,
                bookingMovie.movieTitle,
                createIntent(context, bookingMovie),
            )
        }
    }

    private fun createIntent(
        context: Context,
        bookingMovie: BookingMovieUIModel,
    ): Intent {
        return Intent(context, TicketActivity::class.java).putExtra(BOOKING_MOVIE_KEY, bookingMovie)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
    }

    companion object {
        const val ALARM_CODE = "alarm"
        const val ALARM_TIME = 30
        const val REQUEST_CODE = 925
    }
}
