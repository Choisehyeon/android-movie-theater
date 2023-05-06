package woowacourse.movie.activity.main.contract

interface MainActivityContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        fun saveSettingData(data: Boolean)
    }
}
