package woowacourse.movie.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.dto.movie.MovieDateDto
import woowacourse.movie.dto.movie.MovieTimeDto
import woowacourse.movie.movielist.OnClickListener
import java.time.format.DateTimeFormatter

class HistoryViewHolder(
    private val view: View,
    private val onItemViewClickListener: OnClickListener<BookingMovieDto>,
) :
    RecyclerView.ViewHolder(view) {
    private val reservationTime: TextView = itemView.findViewById(R.id.history_datetime)
    private val reservationMovie: TextView = itemView.findViewById(R.id.history_title)

    fun bind(item: BookingMovieDto) {
        reservationTime.text = formatMovieDateTime(item.date, item.time)
        reservationMovie.text = item.movie.title

        view.setOnClickListener {
            onItemViewClickListener.onClick(item)
        }
    }

    private fun formatMovieDateTime(date: MovieDateDto, time: MovieTimeDto): String {
        val formatDate =
            date.date.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.date_format)))
        val formatTime =
            time.time.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.time_format)))

        return view.context.getString(R.string.reservation_datetime, formatDate, formatTime)
    }
}
