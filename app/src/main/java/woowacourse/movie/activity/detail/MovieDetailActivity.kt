package woowacourse.movie.activity.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import domain.movieinfo.MovieDate
import woowacourse.movie.R
import woowacourse.movie.activity.detail.contract.MovieDetailActivityContract
import woowacourse.movie.activity.detail.contract.presenter.MovieDetailActivityPresenter
import woowacourse.movie.activity.seat.SeatSelectionActivity
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
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
    private var dateSpinnerPosition = 0
    private var timeSpinnerPosition = 0

    private val selectDateSpinner by lazy { findViewById<Spinner>(R.id.select_date) }
    private val selectTimeSpinner by lazy { findViewById<Spinner>(R.id.select_time) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setToolBar()
        setUpState(savedInstanceState)

        val movie =
            intent.intentSerializable(MOVIE_KEY, MovieUIModel::class.java) ?: MovieUIModel.movieData

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
        val toolbar = findViewById<Toolbar>(R.id.movie_detail_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setMovieData(movie: MovieUIModel) {
        val moviePoster = findViewById<ImageView>(R.id.movie_poster)
        val movieTitle = findViewById<TextView>(R.id.movie_title)
        val movieDate = findViewById<TextView>(R.id.movie_date)
        val runningTime = findViewById<TextView>(R.id.movie_time)
        val description = findViewById<TextView>(R.id.movie_description)

        moviePoster.setImageResource(movie.moviePoster)
        movieTitle.text = movie.title

        movieDate.text = formatMovieRunningDate(movie)

        runningTime.text = getString(R.string.movie_running_time).format(movie.runningTime)
        description.text = movie.description
    }

    override fun setDateSpinnerPosition(dateSpinnerPosition: Int) {
        selectDateSpinner.setSelection(dateSpinnerPosition)
    }

    override fun setTimeSpinnerPosition(timeSpinnerPosition: Int) {
        selectTimeSpinner.setSelection(timeSpinnerPosition)
    }

    override fun formatMovieRunningDate(item: MovieUIModel): String {
        val startDate =
            item.startDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val endDate =
            item.endDate.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        return getString(R.string.movie_running_date).format(startDate, endDate)
    }

    override fun setBookerNumber(number: TicketCountUIModel) {
        val booker = findViewById<TextView>(R.id.booker_num)
        booker.text = number.numberOfPeople.toString()
    }

    override fun showSeatSelectPage(
        data: MovieUIModel,
        count: TicketCountUIModel,
        date: MovieDateUIModel,
        time: MovieTimeUIModel,
    ) {
        val intent = Intent(this, SeatSelectionActivity::class.java)
        intent.putExtra(TICKET_KEY, count)
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

        selectDateSpinner.adapter = dateAdapter
    }

    override fun setTimeSpinnerData(data: List<String>) {
        val timeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            data,
        )

        timeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        selectTimeSpinner.adapter = timeAdapter
    }

    private fun onClickDecreaseBtnListener() {
        val minusBtn = findViewById<Button>(R.id.minus_button)

        minusBtn.setOnClickListener {
            presenter.decreaseNum()
        }
    }

    private fun onClickIncreaseBtnListener() {
        val plusBtn = findViewById<Button>(R.id.plus_button)

        plusBtn.setOnClickListener {
            presenter.increaseNum()
        }
    }

    private fun onClickBookBtnListener(movie: MovieUIModel) {
        val bookBtn = findViewById<Button>(R.id.book_button)

        bookBtn.setOnClickListener {
            presenter.onBookBtnClick(
                movie,
                selectDateSpinner.selectedItem.toString(),
                selectTimeSpinner.selectedItem.toString(),
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
        selectDateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectTimeSpinner(
                    MovieDate.of(selectDateSpinner.getItemAtPosition(position) as String)
                        .mapToUIModel(),
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun selectTimeSpinner(selectedDay: MovieDateUIModel) {
        presenter.loadTimeSpinnerData(selectedDay)
        presenter.loadTimeSpinnerPosition(timeSpinnerPosition)

        selectTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        private const val MOVIE_KEY = "movie"
        private const val DATE_KEY = "movie_date"
        private const val TIME_KEY = "movie_time"
        private const val DATE_SPINNER_POSITION = "date_spinner_position"
        private const val TIME_SPINNER_POSITION = "time_spinner_position"
    }
}
