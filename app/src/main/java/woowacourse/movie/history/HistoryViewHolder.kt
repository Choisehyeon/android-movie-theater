package woowacourse.movie.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.movielist.OnClickListener
import java.time.format.DateTimeFormatter

class HistoryViewHolder(
    private val view: View,
    private val onItemViewClickListener: OnClickListener<Int>,
) :
    RecyclerView.ViewHolder(view) {
    private val reservationTime: TextView = itemView.findViewById(R.id.history_datetime)
    private val reservationMovie: TextView = itemView.findViewById(R.id.history_title)

    init {
        view.setOnClickListener {
            onItemViewClickListener.onClick(absoluteAdapterPosition)
        }
    }

    fun bind(item: BookingMovieUIModel) {
        reservationTime.text = formatMovieDateTime(item.date, item.time)
        reservationMovie.text = item.movieTitle
    }

    private fun formatMovieDateTime(date: MovieDateUIModel, time: MovieTimeUIModel): String {
        val formatDate =
            date.date.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.date_format)))
        val formatTime =
            time.time.format(DateTimeFormatter.ofPattern(view.context.getString(R.string.time_format)))

        return view.context.getString(R.string.reservation_datetime, formatDate, formatTime)
    }
}
