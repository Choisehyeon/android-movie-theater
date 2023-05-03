package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

class SettingPreference(context: Context) : DataPreference {

    private val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            setting_preference_key,
            Context.MODE_PRIVATE,
        )

    override fun loadData(): Boolean {
        return sharedPreferences.getBoolean(setting_value_key, false)
    }

    override fun saveData(value: Boolean) {
        sharedPreferences.edit().putBoolean(setting_value_key, value).apply()
    }

    companion object {
        private const val setting_preference_key = "setting"
        private const val setting_value_key = "setting_value"
    }
}
