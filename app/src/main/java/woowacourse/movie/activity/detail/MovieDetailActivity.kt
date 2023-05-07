package woowacourse.movie.activity.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import domain.movieinfo.MovieDate
import woowacourse.movie.R
import woowacourse.movie.activity.detail.contract.MovieDetailActivityContract
import woowacourse.movie.activity.detail.contract.presenter.MovieDetailActivityPresenter
import woowacourse.movie.activity.seat.SeatSelectionActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.movie.mapToUIModel
import woowacourse.movie.util.Extensions.intentSerializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity(), MovieDetailActivityContract.View {

    override val presenter: MovieDetailActivityContract.Presenter by lazy {
        MovieDetailActivityPresenter(
            this,
        )
    }
    private lateinit var binding: ActivityMovieDetailBinding
    private var dateSpinnerPosition = 0
    private var timeSpinnerPosition = 0
    private val movie by lazy {
        intent.intentSerializable(MOVIE_KEY, MovieUIModel::class.java) ?: MovieUIModel.movieData
    }
    private val theater by lazy {
        intent.intentSerializable(
            THEATER_KEY,
            TheaterUIModel::class.java,
        ) ?: TheaterUIModel.theater
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        setToolBar()
        setUpState(savedInstanceState)

        selectDateSpinner(movie.startDate, movie.endDate)
        presenter.loadMovieData(movie)
        onClickDecreaseBtnListener()
        onClickIncreaseBtnListener()
        onClickBookBtnListener(movie)
    }

    override fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            dateSpinnerPosition = savedInstanceState.getInt(DATE_SPINNER_POSITION)
            timeSpinnerPosition = savedInstanceState.getInt(TIME_SPINNER_POSITION)
        }
    }

    override fun setToolBar() {
        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setMovieData(movie: MovieUIModel) {
        binding.moviePoster.setImageResource(movie.moviePoster)
        binding.movieTitle.text = movie.title

        binding.movieDate.text = formatMovieRunningDate(movie)
        binding.movieTime.text = getString(R.string.movie_running_time).format(movie.runningTime)
        binding.movieDescription.text = movie.description
    }

    override fun setDateSpinnerPosition(dateSpinnerPosition: Int) {
        binding.selectDate.setSelection(dateSpinnerPosition)
    }

    override fun setTimeSpinnerPosition(timeSpinnerPosition: Int) {
        binding.selectTime.setSelection(timeSpinnerPosition)
    }

    override fun formatMovieRunningDate(item: MovieUIModel): String {
        val startDate =
            item.startDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val endDate =
            item.endDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        return getString(R.string.movie_running_date).format(startDate, endDate)
    }

    override fun setBookerNumber(number: TicketCountUIModel) {
        binding.bookerNum.text = number.numberOfPeople.toString()
    }

    override fun showSeatSelectPage(
        data: MovieUIModel,
        count: TicketCountUIModel,
        date: MovieDateUIModel,
        time: MovieTimeUIModel,
    ) {
        val intent = Intent(this, SeatSelectionActivity::class.java)
        intent.putExtra(TICKET_KEY, count)
        intent.putExtra(THEATER_KEY, theater)
        intent.putExtra(MOVIE_KEY, data)
        intent.putExtra(DATE_KEY, date)
        intent.putExtra(TIME_KEY, time)
        startActivity(intent)
        finish()
    }

    override fun setDateSpinnerData(data: List<String>) {
        val dateAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            data,
        )
        dateAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        binding.selectDate.adapter = dateAdapter
    }

    override fun setTimeSpinnerData(data: List<String>) {
        val timeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            data,
        )

        timeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.selectTime.adapter = timeAdapter
    }

    private fun onClickDecreaseBtnListener() {
        binding.minusButton.setOnClickListener {
            presenter.decreaseNum()
        }
    }

    private fun onClickIncreaseBtnListener() {
        binding.plusButton.setOnClickListener {
            presenter.increaseNum()
        }
    }

    private fun onClickBookBtnListener(movie: MovieUIModel) {
        binding.bookButton.setOnClickListener {
            presenter.onBookBtnClick(
                movie,
                binding.selectDate.selectedItem.toString(),
                binding.selectTime.selectedItem.toString(),
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun selectDateSpinner(startDate: LocalDate, endDate: LocalDate) {
        presenter.loadDateSpinnerData(startDate, endDate)
        presenter.loadDateSpinnerPosition(dateSpinnerPosition)
        binding.selectDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectTimeSpinner()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun selectTimeSpinner() {
        presenter.loadTimeSpinnerData(movie.id, theater)
        presenter.loadTimeSpinnerPosition(timeSpinnerPosition)

        binding.selectTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                timeSpinnerPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(DATE_SPINNER_POSITION, dateSpinnerPosition)
        outState.putInt(TIME_SPINNER_POSITION, timeSpinnerPosition)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val TICKET_KEY = "ticket"
        const val THEATER_KEY = "theater"
        private const val MOVIE_KEY = "movie"
        private const val DATE_KEY = "movie_date"
        private const val TIME_KEY = "movie_time"
        private const val DATE_SPINNER_POSITION = "date_spinner_position"
        private const val TIME_SPINNER_POSITION = "time_spinner_position"
    }
}
