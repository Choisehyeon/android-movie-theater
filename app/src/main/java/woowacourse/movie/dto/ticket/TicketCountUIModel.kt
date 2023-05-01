package woowacourse.movie.dto.ticket

import java.io.Serializable

data class TicketCountUIModel(
    val numberOfPeople: Int = MIN_BOOKER_NUMBER,
) : Serializable {
    companion object {
        const val MIN_BOOKER_NUMBER = 1
    }
}
