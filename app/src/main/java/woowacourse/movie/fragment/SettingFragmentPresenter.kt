package woowacourse.movie.fragment

import android.content.Context
import woowacourse.movie.SettingPreference

class SettingFragmentPresenter(val view: SettingFragmentContract.View) : SettingFragmentContract.Presenter {
    private val settingPreference = SettingPreference(view.getContext())
    override fun onSaveData(data: Boolean) {
        settingPreference.saveData(data)
    }

    override fun onLoadData() {
        view.setSwitchState(settingPreference.loadData())
    }
}
