package woowacourse.movie.activity

interface MainActivityContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        fun saveSettingData(data: Boolean)
    }
}
