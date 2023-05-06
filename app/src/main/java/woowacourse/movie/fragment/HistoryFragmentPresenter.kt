package woowacourse.movie.fragment

import woowacourse.movie.database.ReservationRepository
import woowacourse.movie.dto.movie.BookingMovieUIModel

class HistoryFragmentPresenter(
    val view: HistoryFragmentContract.View,
    private val repository: ReservationRepository,
) :
    HistoryFragmentContract.Presenter {
    override fun init() {
        view.setRecyclerView()
    }

    override fun loadDatas() {
        view.updateRecyclerView(repository.getAll())
    }

    override fun onHistoryClick(item: BookingMovieUIModel) {
        view.showMovieTicket(item)
    }
}
