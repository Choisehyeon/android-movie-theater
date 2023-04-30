package woowacourse.movie.dto.ticket

import java.io.Serializable

data class TicketPriceDto(
    val price: Int = TICKET_PRICE,
) : Serializable {

    companion object {
        const val TICKET_PRICE = 13000
    }
}
