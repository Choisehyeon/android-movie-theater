package woowacourse.movie.fragment

import android.content.Context
import woowacourse.movie.SettingPreference

class SettingFragmentPresenter(val view: SettingFragmentContract.View) : SettingFragmentContract.Presenter {

    override fun setSettingPreference(context: Context, value: Boolean) {
        SettingPreference.setSetting(context, value)
    }

    override fun setSettingState(context: Context) {
        view.setSwitchState(SettingPreference.getSetting(context))
    }
}
