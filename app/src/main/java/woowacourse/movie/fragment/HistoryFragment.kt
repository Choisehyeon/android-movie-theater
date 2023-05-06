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

    override val presenter: HistoryFragmentContract.Presenter by lazy {
        HistoryFragmentPresenter(
            this,
        )
    }
    private val historyRecyclerView: RecyclerView by lazy { requireView().findViewById(R.id.history_rv)}

    private val onItemClick = object : OnClickListener<BookingMovieUIModel> {
        override fun onClick(item: BookingMovieUIModel) {
            presenter.onHistoryClick(item)
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
        presenter.loadDatas()
    }

    override fun setRecyclerView(histories: List<BookingMovieUIModel>) {
        val historyRVAdapter = HistoryRecyclerViewAdapter(
            histories,
            onItemClick,
        )
        historyRecyclerView.adapter = historyRVAdapter
    }

    override fun showMovieTicket(data: BookingMovieUIModel) {
        val intent = Intent(context, TicketActivity::class.java)
        intent.putExtra(BOOKING_MOVIE_KEY, data)
        startActivity(intent)
    }

    companion object {
        private const val BOOKING_MOVIE_KEY = "booking_movie"
        const val TAG = "history_fragment"
    }
}
