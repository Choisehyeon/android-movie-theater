package woowacourse.movie.fragment.setting.contract.presenter

import woowacourse.movie.util.preference.DataPreference
import woowacourse.movie.fragment.setting.contract.SettingFragmentContract

class SettingFragmentPresenter(val view: SettingFragmentContract.View, private val dataPreference: DataPreference) :
    SettingFragmentContract.Presenter {

    override fun onSaveData(data: Boolean) {
        dataPreference.saveData(data)
    }

    override fun onLoadData() {
        view.setSwitchState(dataPreference.loadData())
    }
}
