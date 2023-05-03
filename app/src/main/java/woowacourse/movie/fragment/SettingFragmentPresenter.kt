package woowacourse.movie.fragment

import android.preference.Preference
import woowacourse.movie.DataPreference
import woowacourse.movie.SettingPreference

class SettingFragmentPresenter(val view: SettingFragmentContract.View, val dataPreference: DataPreference) :
    SettingFragmentContract.Presenter {

    override fun onSaveData(data: Boolean) {
        dataPreference.saveData(data)
    }

    override fun onLoadData() {
        view.setSwitchState(dataPreference.loadData())
    }
}
