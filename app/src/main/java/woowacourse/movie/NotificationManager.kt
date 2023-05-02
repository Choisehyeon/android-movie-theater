package woowacourse.movie

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationManager {

    private const val CHANNEL_ID = "alarm_channel"

    private fun createNotificationPendingIntent(
        context: Context,
        intent: Intent,
    ): PendingIntent {
        return PendingIntent.getActivity(
            context,
            AlarmReceiver.REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_text, AlarmReceiver.ALARM_TIME)
        val channel =
            NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotificationBuilder(context: Context, title: String, intent: Intent) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.cute_android)
            setContentTitle(context.getString(R.string.notification_title))
            setContentText(
                context.getString(
                    R.string.notification_text,
                    title,
                    AlarmReceiver.ALARM_TIME,
                ),
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(createNotificationPendingIntent(context, intent))
            setAutoCancel(true)
        }
        notifyBuilder(context, builder)
    }

    private fun notifyBuilder(context: Context, builder: NotificationCompat.Builder) {
        if (PermissionManager.checkPermissionGranted(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            )
        ) {
            NotificationManagerCompat.from(context)
                .notify(AlarmReceiver.REQUEST_CODE, builder.build())
            return
        }
    }
}
