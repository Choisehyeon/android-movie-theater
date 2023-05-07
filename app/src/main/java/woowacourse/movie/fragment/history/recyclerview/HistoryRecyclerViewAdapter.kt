package woowacourse.movie.fragment.history.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HistoryItemBinding
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.util.listener.OnClickListener

class HistoryRecyclerViewAdapter(
    private var histories: List<BookingMovieUIModel>,
    private val onItemClickListener: OnClickListener<BookingMovieUIModel>,

) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    private lateinit var binding: HistoryItemBinding
    private val itemViewClick = object : OnClickListener<Int> {
        override fun onClick(item: Int) {
            onItemClickListener.onClick(histories[item])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, itemViewClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = histories[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDatas(histories: List<BookingMovieUIModel>) {
        this.histories = histories
        notifyDataSetChanged()
    }
}
