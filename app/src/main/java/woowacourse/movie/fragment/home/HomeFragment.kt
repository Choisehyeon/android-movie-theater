package woowacourse.movie.fragment.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.detail.MovieDetailActivity
import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.fragment.home.contract.HomeFragmentContract
import woowacourse.movie.fragment.home.contract.presenter.HomeFragmentPresenter
import woowacourse.movie.fragment.home.recyclerview.MovieRecyclerViewAdapter
import woowacourse.movie.util.listener.OnClickListener

class HomeFragment : Fragment(), HomeFragmentContract.View {

    override val presenter: HomeFragmentContract.Presenter by lazy { HomeFragmentPresenter(this) }
    private val movieRV: RecyclerView by lazy { requireView().findViewById(R.id.movie_rv) }
    private lateinit var movieRecyclerViewAdatper: MovieRecyclerViewAdapter

    private val movieItemClick = object : OnClickListener<MovieUIModel> {
        override fun onClick(item: MovieUIModel) {
            presenter.onMovieItemClick(item)
        }
    }

    private val adItemClick = object : OnClickListener<AdUIModel> {
        override fun onClick(item: AdUIModel) {
            presenter.onAdItemClick(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.loadDatas()
    }

    override fun setRecyclerView(movies: List<MovieUIModel>, ad: AdUIModel) {
        movieRecyclerViewAdatper = MovieRecyclerViewAdapter(
            movies,
            ad,
            movieItemClick,
            adItemClick,
        )
        movieRV.adapter = movieRecyclerViewAdatper
    }

    override fun showMovieDetail(data: MovieUIModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_KEY, data)
        startActivity(intent)
    }

    override fun showAd(data: AdUIModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
        startActivity(intent)
    }

    companion object {
        private const val MOVIE_KEY = "movie"
        const val TAG = "home_fragment"
    }
}
