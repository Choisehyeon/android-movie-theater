package woowacourse.movie.dto.movie

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.fragment.home.recyclerview.ViewType

data class AdUIModel(
    val viewType: ViewType = ViewType.AD_VIEW,
    @DrawableRes val adImage: Int,
    val url: String,
) {
    companion object {
        fun getAdData(): AdUIModel {
            return AdUIModel(
                adImage = R.drawable.ad,
                url = "https://woowacourse.github.io/",
            )
        }
    }
}
