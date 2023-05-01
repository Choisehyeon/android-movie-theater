package woowacourse.movie

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.activity.MainActivity
import woowacourse.movie.activity.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.activity.TicketActivity
import woowacourse.movie.dto.movie.BookingMovieUIModel

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ALARM_CODE && SettingPreference.getSetting(context)) {
            createNotificationChannel(context)

            val bookingMovie = intent.getSerializableExtra(BOOKING_MOVIE_KEY) as BookingMovieUIModel
            Log.d(BOOKING_MOVIE_KEY, bookingMovie.toString())

            createNotificationBuilder(context, bookingMovie)
        }
    }

    private fun createNotificationPendingIntent(
        context: Context,
        bookingMovie: BookingMovieUIModel,
    ): PendingIntent {
        val ticketIntent =
            Intent(context, TicketActivity::class.java).putExtra(BOOKING_MOVIE_KEY, bookingMovie)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

        return PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            ticketIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_text, ALARM_TIME)
        val channel =
            NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationBuilder(context: Context, bookingMovie: BookingMovieUIModel) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.cute_android)
            setContentTitle(context.getString(R.string.notification_title))
            setContentText(
                context.getString(
                    R.string.notification_text,
                    bookingMovie.movie.title,
                    ALARM_TIME,
                ),
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(createNotificationPendingIntent(context, bookingMovie))
            setAutoCancel(true)
        }
        notifyBuilder(context, builder)
    }

    private fun notifyBuilder(context: Context, builder: NotificationCompat.Builder) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(REQUEST_CODE, builder.build())
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            (context as MainActivity).requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    companion object {
        const val ALARM_CODE = "alarm"
        const val ALARM_TIME = 30
        private const val CHANNEL_ID = "alarm_channel"
        const val REQUEST_CODE = 925
    }
}
