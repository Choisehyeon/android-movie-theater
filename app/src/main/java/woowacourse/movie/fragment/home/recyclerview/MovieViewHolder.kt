package woowacourse.movie.fragment.home.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.util.listener.OnClickListener
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val onItemClickListener: OnClickListener<Int>,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.bookButton.setOnClickListener {
            onItemClickListener.onClick(adapterPosition)
        }
    }

    fun bind(movie: MovieUIModel) {
        binding.moviePoster.setImageResource(movie.moviePoster)
        binding.movieTitle.text = movie.title
        binding.movieDate.text = formatMovieRunningDate(movie)
        binding.runningTime.text = formatMovieRunningTime(movie)
    }

    private fun formatMovieRunningDate(item: MovieUIModel): String {
        val startDate =
            item.startDate.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        val endDate =
            item.endDate.format(DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)))
        return binding.root.context.getString(R.string.movie_running_date, startDate, endDate)
    }

    private fun formatMovieRunningTime(item: MovieUIModel): String {
        return binding.root.context.getString(R.string.movie_running_time).format(item.runningTime)
    }
}
