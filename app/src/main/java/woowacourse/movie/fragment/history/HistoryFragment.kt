package woowacourse.movie.fragment.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.activity.ticket.TicketActivity
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.database.ReservationRepository
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.fragment.history.contract.HistoryFragmentContract
import woowacourse.movie.fragment.history.contract.presenter.HistoryFragmentPresenter
import woowacourse.movie.fragment.history.recyclerview.HistoryRecyclerViewAdapter
import woowacourse.movie.util.listener.OnClickListener

class HistoryFragment : Fragment(), HistoryFragmentContract.View {

    override val presenter: HistoryFragmentContract.Presenter by lazy {
        HistoryFragmentPresenter(
            this,
            ReservationRepository(ReservationDatabase.getDatabase(requireContext())),
        )
    }
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyRVAdapter: HistoryRecyclerViewAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.init()
        presenter.loadDatas()
    }

    override fun setRecyclerView() {
        historyRVAdapter = HistoryRecyclerViewAdapter(
            listOf(),
            onItemClick,
        )
        binding.historyRv.adapter = historyRVAdapter
    }

    override fun updateRecyclerView(histories: List<BookingMovieUIModel>) {
        historyRVAdapter.updateDatas(histories)
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
