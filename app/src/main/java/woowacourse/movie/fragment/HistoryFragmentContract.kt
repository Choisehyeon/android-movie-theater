package woowacourse.movie.fragment

import woowacourse.movie.dto.movie.BookingMovieUIModel

interface HistoryFragmentContract {
    interface View {
        val presenter: Presenter
        fun setRecyclerView(histories: List<BookingMovieUIModel>)
        fun showMovieTicket(data: BookingMovieUIModel)
    }

    interface Presenter {
        fun loadDatas()
        fun onHistoryClick(item: BookingMovieUIModel)
    }
}
