package woowacourse.movie

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionManager {

    fun requestNotificationPermission(
        activity: Activity,
        permission: String,
        requestPermissionLauncher: ActivityResultLauncher<String>,
    ) {
        if (checkPermissionGranted(activity, permission)) return
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && activity.shouldShowRequestPermissionRationale(
                Manifest.permission.POST_NOTIFICATIONS,
            ) -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.check_notification),
                    Toast.LENGTH_LONG,
                )
                    .show()
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            else -> {}
        }
    }

    fun checkPermissionGranted(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
}
