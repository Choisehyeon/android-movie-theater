package woowacourse.movie.fragment

import android.content.Context

interface SettingFragmentContract {
    interface View {
        var presenter: Presenter
        fun setSwitchState(value: Boolean)
        fun getContext(): Context
    }
    interface Presenter {
        fun onSaveData(data: Boolean)
        fun onLoadData()
    }
}
