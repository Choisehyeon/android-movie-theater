package woowacourse.movie.fragment.home.contract

import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel

interface HomeFragmentContract {
    interface View {
        val presenter: Presenter
        fun setRecyclerView(movies: List<MovieUIModel>, ad: AdUIModel)
        fun showTheaterFragment(movie: MovieUIModel, theaters: List<TheaterUIModel>)
        fun showAd(data: AdUIModel)
    }

    interface Presenter {
        fun loadDatas()
        fun onMovieItemClick(item: MovieUIModel)
        fun onAdItemClick(item: AdUIModel)
    }
}
