package woowacourse.movie.fragment

import woowacourse.movie.dto.AdUIModel
import woowacourse.movie.dto.movie.MovieUIModel

interface HomeFragmentContract {
    interface View {
        var presenter: Presenter
        fun setRecyclerView(movies: List<MovieUIModel>, ad: AdUIModel)
        fun showMovieDetail(data: MovieUIModel)
        fun showAd(data: AdUIModel)
    }

    interface Presenter {
        fun loadDatas()
        fun onMovieItemClick(item: MovieUIModel)
        fun onAdItemClick(item: AdUIModel)
    }
}
