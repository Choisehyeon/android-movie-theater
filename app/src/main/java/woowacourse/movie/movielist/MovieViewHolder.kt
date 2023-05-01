import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.movielist.OnClickListener
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    private val view: View,
    private val onItemClickListener: OnClickListener<Int>,
) :
    RecyclerView.ViewHolder(view) {
    private val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
    private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
    private val movieDate: TextView = itemView.findViewById(R.id.movie_date)
    private val runningTime: TextView = itemView.findViewById(R.id.running_time)
    private val bookButton: Button = itemView.findViewById(R.id.book_button)

    init {
        bookButton.setOnClickListener {
            onItemClickListener.onClick(adapterPosition)
        }
    }

    fun bind(movie: MovieDto) {
        moviePoster.setImageResource(movie.moviePoster)
        movieTitle.text = movie.title
        movieDate.text = formatMovieRunningDate(movie)
        runningTime.text = formatMovieRunningTime(movie)
    }

    private fun formatMovieRunningDate(item: MovieDto): String {
        val startDate =
            item.startDate.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.date_format)))
        val endDate =
            item.endDate.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.date_format)))
        return view.context.getString(R.string.movie_running_date, startDate, endDate)
    }

    private fun formatMovieRunningTime(item: MovieDto): String {
        return view.context.getString(R.string.movie_running_time).format(item.runningTime)
    }
}
