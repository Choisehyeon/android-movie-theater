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
import woowacourse.movie.history.HistoryRecyclerViewAdapter
import woowacourse.movie.movielist.OnClickListener

class HistoryFragment : Fragment() {

    private val onItemClick = object : OnClickListener<BookingMovieDto> {
        override fun onClick(item: BookingMovieDto) {
            val intent = Intent(context, TicketActivity::class.java)
            intent.putExtra(BOOKING_MOVIE_KEY, item)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpHistoryDatas(view)
    }

    private fun setUpHistoryDatas(view: View) {
        val historyRecyclerView = view.findViewById<RecyclerView>(R.id.history_rv)
        val historyRVAdapter = HistoryRecyclerViewAdapter(
            BookingHistoryDto.getHistory(),
            onItemClick,
        )

        historyRecyclerView.adapter = historyRVAdapter
    }

    companion object {
        private const val BOOKING_MOVIE_KEY = "booking_movie"
    }
}
