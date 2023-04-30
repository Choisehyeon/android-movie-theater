package woowacourse.movie.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.movielist.OnClickListener

class HistoryRecyclerViewAdapter(
    private val histories: List<BookingMovieDto>,
) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    lateinit var itemViewClick: OnClickListener<BookingMovieDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view, itemViewClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = histories[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return histories.size
    }
}
