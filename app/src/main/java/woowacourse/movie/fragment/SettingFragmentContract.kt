package woowacourse.movie.fragment

import android.content.Context

interface SettingFragmentContract {
    interface View {
        var presenter: Presenter
        fun setSwitchState(value: Boolean)
    }
    interface Presenter {
        fun setSettingPreference(context: Context, value: Boolean)
        fun setSettingState(context: Context)
    }
}
