package woowacourse.movie.fragment.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.util.listener.OnClickListener

class MovieRecyclerViewAdapter(
    private val movies: List<MovieUIModel>,
    private val ad: AdUIModel,
    private val onMovieItemClickListener: OnClickListener<MovieUIModel>,
    private val onAdItemClickListener: OnClickListener<AdUIModel>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var movieBinding: MovieItemBinding
    private lateinit var adBinding: AdItemBinding

    private val onMovieItemViewClickListener = object : OnClickListener<Int> {
        override fun onClick(item: Int) {
            onMovieItemClickListener.onClick(getMovieItem(item))
        }
    }

    private val onAdItemViewClickListener = object : OnClickListener<Int> {
        override fun onClick(item: Int) {
            onAdItemClickListener.onClick(ad)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.MOVIE_VIEW -> {
                movieBinding =
                    MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(movieBinding, onMovieItemViewClickListener)
            }
            ViewType.AD_VIEW -> {
                adBinding =
                    AdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(adBinding, onAdItemViewClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem = getMovieItem(position)
        when (holder.itemViewType) {
            ViewType.MOVIE_VIEW.ordinal -> (holder as MovieViewHolder).bind(movieItem)
            ViewType.AD_VIEW.ordinal -> (holder as AdViewHolder).bind(ad)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % DIVIDE_MOVIE_VIEW == 0) ViewType.AD_VIEW.ordinal else ViewType.MOVIE_VIEW.ordinal
    }

    override fun getItemCount(): Int {
        return movies.size + 1
    }

    private fun getMovieItem(position: Int): MovieUIModel =
        movies[position - (position / DIVIDE_MOVIE_VIEW)]

    companion object {
        private const val DIVIDE_MOVIE_VIEW = 4
    }
}
