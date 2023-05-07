package woowacourse.movie.dto.movie

data class TheaterUIModel(
    val name: String,
    val screeningTime: Map<Int, List<String>>,
) : java.io.Serializable {
    companion object {
        val theater = TheaterUIModel(
            name = "",
            screeningTime = mapOf(
                1 to listOf(""),
            ),
        )
    }
}
