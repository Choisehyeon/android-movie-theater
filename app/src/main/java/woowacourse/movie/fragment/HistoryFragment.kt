package woowacourse.movie.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.TicketActivity
import woowacourse.movie.dto.BookingHistoryDto
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.history.HistoryRVAdapter
import woowacourse.movie.movielist.OnClickListener

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        setUpHistoryDatas(view)

        return view
    }

    private fun setUpHistoryDatas(view: View) {
        val history_rv = view.findViewById<RecyclerView>(R.id.history_rv)
        val historyRVAdapter = HistoryRVAdapter(
            BookingHistoryDto.getHistory(),
        )

        historyRecyclerView.adapter = historyRVAdapter
        historyRVAdapter.notifyDataSetChanged()
        historyRVAdapter.itemViewClick = object : OnClickListener<BookingMovieDto> {
            override fun onClick(item: BookingMovieDto) {
                val intent = Intent(context, TicketActivity::class.java)
                intent.putExtra(BOOKING_MOVIE_KEY, item)
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val BOOKING_MOVIE_KEY = "booking_movie"
    }
}
