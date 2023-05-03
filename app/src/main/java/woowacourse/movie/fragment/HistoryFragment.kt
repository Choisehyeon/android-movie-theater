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
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.history.HistoryRecyclerViewAdapter
import woowacourse.movie.movielist.OnClickListener

class HistoryFragment : Fragment(), HistoryFragmentContract.View {

    override lateinit var presenter: HistoryFragmentContract.Presenter
    private lateinit var historyRecyclerView: RecyclerView

    private val onItemClick = object : OnClickListener<BookingMovieUIModel> {
        override fun onClick(item: BookingMovieUIModel) {
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
        presenter = HistoryFragmentPresenter(this)
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyRecyclerView = view.findViewById(R.id.history_rv)
        presenter.loadDatas()
    }

    override fun setRecyclerView(histories: List<BookingMovieUIModel>) {
        val historyRVAdapter = HistoryRecyclerViewAdapter(
            histories,
            onItemClick,
        )
        historyRecyclerView.adapter = historyRVAdapter
    }

    companion object {
        private const val BOOKING_MOVIE_KEY = "booking_movie"
    }
}
