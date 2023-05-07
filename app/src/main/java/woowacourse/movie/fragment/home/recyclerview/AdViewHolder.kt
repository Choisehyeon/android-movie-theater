package woowacourse.movie.fragment.home.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.util.listener.OnClickListener

class AdViewHolder(
    private val binding: AdItemBinding,
    private val onItemClickListener: OnClickListener<Int>,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ad.setOnClickListener {
            onItemClickListener.onClick(absoluteAdapterPosition)
        }
    }

    fun bind(item: AdUIModel) {
        binding.ad.setImageResource(item.adImage)
    }
}
