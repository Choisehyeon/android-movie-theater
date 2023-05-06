package woowacourse.movie.fragment.history.contract.presenter

import woowacourse.movie.database.ReservationRepository
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.fragment.history.contract.HistoryFragmentContract

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
