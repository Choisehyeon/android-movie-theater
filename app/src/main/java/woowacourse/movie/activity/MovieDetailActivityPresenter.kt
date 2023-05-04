package woowacourse.movie.activity

import domain.DayOfWeek
import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime
import domain.screeningschedule.ReservationDate
import domain.screeningschedule.ReservationTime
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.movie.mapToUIModel
import woowacourse.movie.mapper.ticket.mapToDomain
import woowacourse.movie.mapper.ticket.mapToUIModel
import java.time.LocalDate

class MovieDetailActivityPresenter(private val view: MovieDetailActivityContract.View) :
    MovieDetailActivityContract.Presenter {
    private var ticketCount = TicketCountUIModel()

    override fun loadMovieData(data: MovieUIModel) {
        view.setMovieData(data)
    }

    override fun loadDateSpinnerPosition(dateSpinnerPosition: Int) {
        view.setDateSpinnerPosition(dateSpinnerPosition)
    }

    override fun loadTimeSpinnerPosition(timeSpinnerPosition: Int) {
        view.setTimeSpinnerPosition(timeSpinnerPosition)
    }

    override fun increaseNum() {
        val ticketIncrease = ticketCount.mapToDomain().increase()
        ticketCount = ticketIncrease.mapToUIModel()
        view.setBookerNumber(ticketCount)
    }

    override fun decreaseNum() {
        val ticketDecrease = ticketCount.mapToDomain().decrease()
        ticketCount = ticketDecrease.mapToUIModel()
        view.setBookerNumber(ticketCount)
    }

    override fun onBookBtnClick(
        data: MovieUIModel,
        date: String,
        time: String,
    ) {
        val selectedDate = MovieDate.of(date)
        val selectedTime = MovieTime.of(time)
        view.showSeatSelectPage(data, ticketCount, selectedDate.mapToUIModel(), selectedTime.mapToUIModel())
    }

    override fun loadDateSpinnerData(startDate: LocalDate, endDate: LocalDate) {
        val data = ReservationDate(startDate, endDate).getIntervalDays()
        view.setDateSpinnerData(data)
    }

    override fun loadTimeSpinnerData(selectedDay: MovieDateUIModel) {
        val data = ReservationTime(DayOfWeek.checkDayOfWeek(selectedDay.date)).getIntervalTimes()
        view.setTimeSpinnerData(data)
    }
}
