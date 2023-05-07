package woowacourse.movie.fragment.theater

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.activity.detail.MovieDetailActivity
import woowacourse.movie.activity.detail.MovieDetailActivity.Companion.THEATER_KEY
import woowacourse.movie.activity.seat.SeatSelectionActivity.Companion.MOVIE_KEY
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.fragment.theater.recyclerview.TheaterRecyclerViewAdapter
import woowacourse.movie.util.listener.OnClickListener

class TheaterFragment(val movie: MovieUIModel, private val theaters: List<TheaterUIModel>) :
    BottomSheetDialogFragment(R.layout.fragment_theater) {

    private lateinit var theaterRecyclerViewAdapter: TheaterRecyclerViewAdapter

    private val onItemClick = object : OnClickListener<TheaterUIModel> {
        override fun onClick(item: TheaterUIModel) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_KEY, movie)
            intent.putExtra(THEATER_KEY, item)
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("theaters2", theaters.toString())
        val theaterRecyclerView = view.findViewById<RecyclerView>(R.id.theater_recyclerview)
        theaterRecyclerViewAdapter = TheaterRecyclerViewAdapter(
            movie,
            theaters,
            onItemClick,
        )
        theaterRecyclerView.adapter = theaterRecyclerViewAdapter
    }

    companion object {
        const val TAG = "theater_fragment"
    }
}
