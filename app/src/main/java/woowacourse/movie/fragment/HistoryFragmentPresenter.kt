package woowacourse.movie.fragment

import woowacourse.movie.dto.BookingHistoryUIModel

class HistoryFragmentPresenter(val view: HistoryFragmentContract.View) :
    HistoryFragmentContract.Presenter {
    override fun loadDatas() {
        view.setRecyclerView(BookingHistoryUIModel.getHistory())
    }
}
