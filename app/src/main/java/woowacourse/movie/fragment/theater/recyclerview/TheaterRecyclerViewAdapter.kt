package woowacourse.movie.fragment.theater.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.util.listener.OnClickListener

class TheaterRecyclerViewAdapter(
    private val movie: MovieUIModel,
    private val theaters: List<TheaterUIModel>,
    private val onItemClickListener: OnClickListener<TheaterUIModel>,
) : RecyclerView.Adapter<TheaterViewHolder>() {

    private lateinit var binding: TheaterItemBinding
    private val itemClick = object : OnClickListener<Int> {
        override fun onClick(item: Int) {
            onItemClickListener.onClick(theaters[item])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        binding = TheaterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int {
        return theaters.size
    }

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        val item = theaters[position]
        holder.bind(movie.id, item)
    }
}
