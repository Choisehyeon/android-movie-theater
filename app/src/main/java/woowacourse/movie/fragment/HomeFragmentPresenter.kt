package woowacourse.movie.fragment

import woowacourse.movie.dto.AdUIModel
import woowacourse.movie.dto.movie.MovieDummy
import woowacourse.movie.dto.movie.MovieUIModel

class HomeFragmentPresenter(val view: HomeFragmentContract.View) : HomeFragmentContract.Presenter {
    override fun loadDatas() {
        view.setRecyclerView(MovieDummy.movieDatas, AdUIModel.getAdData())
    }

    override fun onMovieItemClick(item: MovieUIModel) {
        view.showMovieDetail(item)
    }

    override fun onAdItemClick(item: AdUIModel) {
        view.showAd(item)
    }
}
