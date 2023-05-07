package woowacourse.movie.activity.detail.contract

import android.os.Bundle
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import java.time.LocalDate

interface MovieDetailActivityContract {
    interface View {
        val presenter: Presenter
        fun setUpState(savedInstanceState: Bundle?)
        fun setToolBar()
        fun setMovieData(data: MovieUIModel)
        fun setDateSpinnerPosition(dateSpinnerPosition: Int)
        fun setTimeSpinnerPosition(timeSpinnerPosition: Int)
        fun formatMovieRunningDate(item: MovieUIModel): String
        fun setBookerNumber(number: TicketCountUIModel)
        fun showSeatSelectPage(
            data: MovieUIModel,
            count: TicketCountUIModel,
            date: MovieDateUIModel,
            time: MovieTimeUIModel,
        )
        fun setDateSpinnerData(data: List<String>)
        fun setTimeSpinnerData(data: List<String>)
    }

    interface Presenter {
        fun loadMovieData(data: MovieUIModel)
        fun loadDateSpinnerPosition(dateSpinnerPosition: Int)
        fun loadTimeSpinnerPosition(timeSpinnerPosition: Int)
        fun increaseNum()
        fun decreaseNum()
        fun onBookBtnClick(
            data: MovieUIModel,
            date: String,
            time: String,
        )
        fun loadDateSpinnerData(startDate: LocalDate, endDate: LocalDate)
        fun loadTimeSpinnerData(movieId: Int, theater: TheaterUIModel)
    }
}
