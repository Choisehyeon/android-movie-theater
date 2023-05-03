package woowacourse.movie.fragment

import woowacourse.movie.dto.movie.BookingMovieUIModel

interface HistoryFragmentContract {
    interface View {
        var presenter: Presenter
        fun setRecyclerView(histories: List<BookingMovieUIModel>)
    }

    interface Presenter {
        fun loadDatas()
    }
}
