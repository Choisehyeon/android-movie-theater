package woowacourse.movie.fragment.home.contract.presenter

import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.dto.movie.MovieDummy
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.fragment.home.contract.HomeFragmentContract

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
