package woowacourse.movie.fragment.history.contract

import woowacourse.movie.dto.movie.BookingMovieUIModel

interface HistoryFragmentContract {
    interface View {
        val presenter: Presenter
        fun setRecyclerView()
        fun updateRecyclerView(histories: List<BookingMovieUIModel>)
        fun showMovieTicket(data: BookingMovieUIModel)
    }

    interface Presenter {
        fun init()
        fun loadDatas()
        fun onHistoryClick(item: BookingMovieUIModel)
    }
}
