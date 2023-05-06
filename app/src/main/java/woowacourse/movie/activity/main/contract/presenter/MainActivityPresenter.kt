package woowacourse.movie.activity.main.contract.presenter

import woowacourse.movie.util.preference.DataPreference
import woowacourse.movie.activity.main.contract.MainActivityContract

class MainActivityPresenter(
    val view: MainActivityContract.View,
    private val dataPreference: DataPreference,
) : MainActivityContract.Presenter {
    override fun saveSettingData(data: Boolean) {
        dataPreference.saveData(data)
    }
}
