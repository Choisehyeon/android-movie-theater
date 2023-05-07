package woowacourse.movie.dto.movie

object TheaterDummy {

    val theaterDatas = listOf<TheaterUIModel>(
        TheaterUIModel("선릉", mapOf(1 to listOf("9:00", "11:00"), 2 to listOf("10:00", "12:00"))),
        TheaterUIModel(
            "잠실",
            mapOf(1 to listOf("9:00", "11:00", "13:00"), 5 to listOf("10:00", "12:00")),
        ),
        TheaterUIModel(
            "강남",
            mapOf(
                10 to listOf("9:00", "11:00", "13:00", "20:00"),
                15 to listOf("10:00", "12:00", "15:00"),
            ),
        ),
    )
}
