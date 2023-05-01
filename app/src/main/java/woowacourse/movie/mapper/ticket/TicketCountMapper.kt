package woowacourse.movie.mapper.ticket

import domain.TicketCount
import woowacourse.movie.dto.ticket.TicketCountUIModel

fun TicketCountUIModel.mapToDomain(): TicketCount {
    return TicketCount(this.numberOfPeople)
}

fun TicketCount.mapToUIModel(): TicketCountUIModel {
    return TicketCountUIModel(this.numberOfPeople)
}
