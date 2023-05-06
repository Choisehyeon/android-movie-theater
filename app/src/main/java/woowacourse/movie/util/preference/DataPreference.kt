package woowacourse.movie.util.preference

interface DataPreference {
    fun loadData(): Boolean
    fun saveData(value: Boolean)
}
