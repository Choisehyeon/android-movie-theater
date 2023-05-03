package woowacourse.movie

interface DataPreference {
    fun loadData(): Boolean
    fun saveData(value: Boolean)
}
