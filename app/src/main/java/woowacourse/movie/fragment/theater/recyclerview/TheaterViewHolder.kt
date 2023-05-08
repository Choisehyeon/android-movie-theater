package woowacourse.movie.fragment.theater.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.util.listener.OnClickListener

class TheaterViewHolder(
    private val binding: TheaterItemBinding,
    private val onItemClickListener: OnClickListener<Int>,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clickButton.setOnClickListener {
            onItemClickListener.onClick(absoluteAdapterPosition)
        }
    }

    fun bind(movieId: Int, item: TheaterUIModel) {
        binding.theater = item
        binding.movieId = movieId
    }
}
