package woowacourse.movie.fragment

import woowacourse.movie.dto.AdUIModel
import woowacourse.movie.dto.movie.MovieDummy

class HomeFragmentPresenter(val view: HomeFragmentContract.View) : HomeFragmentContract.Presenter {
    override fun loadDatas() {
        view.setRecyclerView(MovieDummy.movieDatas, AdUIModel.getAdData())
    }
}
