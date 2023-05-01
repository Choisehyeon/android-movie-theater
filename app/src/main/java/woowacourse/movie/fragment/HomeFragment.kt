package woowacourse.movie.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.MovieDetailActivity
import woowacourse.movie.dto.AdDto
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.dto.movie.MovieDummy
import woowacourse.movie.movielist.MovieRecyclerViewAdapter
import woowacourse.movie.movielist.OnClickListener

class HomeFragment : Fragment() {

    private fun setUpMovieDatas(view: View) {
        val movieRV = view.findViewById<RecyclerView>(R.id.movie_rv)
        val movieRVAdapter = MovieRecyclerViewAdapter(
            MovieDummy.movieDatas,
            AdDto.getAdData(),
        )

        movieRV.adapter = movieRVAdapter
        onMovieItemClickListener(movieRVAdapter)
        onAdItemClickListener(movieRVAdapter)

        movieRVAdapter.notifyDataSetChanged()
    }

    private fun onMovieItemClickListener(adapter: MovieRecyclerViewAdapter) {
        adapter.itemMovieClick = object : OnClickListener<MovieDto> {
            override fun onClick(item: MovieDto) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(MOVIE_KEY, item)
                startActivity(intent)
            }
        }
    }

    private fun onAdItemClickListener(adapter: MovieRecyclerViewAdapter) {
        adapter.itemAdClick = object : OnClickListener<AdDto> {
            override fun onClick(item: AdDto) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(intent)
            }
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
        super.onViewCreated(view, savedInstanceState)
        setUpMovieDatas(view)
    }

    companion object {
        private const val MOVIE_KEY = "movie"
    }
}
