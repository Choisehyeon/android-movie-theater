package woowacourse.movie.activity

import woowacourse.movie.DataPreference

class MainActivityPresenter(
    val view: MainActivityContract.View,
    private val dataPreference: DataPreference,
) : MainActivityContract.Presenter {
    override fun saveSettingData(data: Boolean) {
        dataPreference.saveData(data)
    }
}
