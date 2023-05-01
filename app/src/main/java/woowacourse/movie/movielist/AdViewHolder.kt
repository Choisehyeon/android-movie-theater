package woowacourse.movie.movielist

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.AdUIModel

class AdViewHolder(private val view: View, private val onItemClickListener: OnClickListener<Int>) :
    RecyclerView.ViewHolder(view) {
    private val adImg = itemView.findViewById<ImageView>(R.id.ad)
    init {
        adImg.setOnClickListener {
            onItemClickListener.onClick(absoluteAdapterPosition)
        }
    }

    fun bind(item: AdUIModel) {
        adImg.setImageResource(item.adImage)
    }
}
