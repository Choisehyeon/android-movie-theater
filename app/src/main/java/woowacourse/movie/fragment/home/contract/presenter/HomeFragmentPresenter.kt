package woowacourse.movie.fragment.home.contract.presenter

import woowacourse.movie.database.TheaterRepository
import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.dto.movie.MovieDummy
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.fragment.home.contract.HomeFragmentContract

class HomeFragmentPresenter(
    val view: HomeFragmentContract.View,
    private val repository: TheaterRepository,
) : HomeFragmentContract.Presenter {
    override fun loadDatas() {
        view.setRecyclerView(MovieDummy.movieDatas, AdUIModel.getAdData())
    }

    override fun onMovieItemClick(item: MovieUIModel) {
        val theaters = loadTheaters(item)
        view.showTheaterFragment(item, theaters)
    }

    private fun loadTheaters(item: MovieUIModel): List<TheaterUIModel> {
        return repository.getTheaterByMovieId(item.id)
    }

    override fun onAdItemClick(item: AdUIModel) {
        view.showAd(item)
    }
}
