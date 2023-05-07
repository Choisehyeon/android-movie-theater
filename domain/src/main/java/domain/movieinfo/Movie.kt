package domain.movieinfo

import java.time.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val description: String,
    val moviePoster: Int,
)
