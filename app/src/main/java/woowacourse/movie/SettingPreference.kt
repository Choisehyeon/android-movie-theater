package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences
object SettingPreference {
    private const val setting_preference_key = "setting"
    private const val setting_value_key = "setting_value"
    fun getSetting(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences(
            setting_preference_key,
            Context.MODE_PRIVATE,
        )

        return sharedPreferences.getBoolean(setting_value_key, false)
    }

    fun setSetting(context: Context, value: Boolean) {
        val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences(
            setting_preference_key,
            Context.MODE_PRIVATE,
        )

        sharedPreferences.edit().putBoolean(setting_value_key, value).apply()
    }
}
