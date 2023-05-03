package woowacourse.movie.fragment

import woowacourse.movie.dto.BookingHistoryUIModel
import woowacourse.movie.dto.movie.BookingMovieUIModel

class HistoryFragmentPresenter(val view: HistoryFragmentContract.View) :
    HistoryFragmentContract.Presenter {
    override fun loadDatas() {
        view.setRecyclerView(BookingHistoryUIModel.getHistory())
    }

    override fun onHistoryClick(item: BookingMovieUIModel) {
        view.showMovieTicket(item)
    }
}
