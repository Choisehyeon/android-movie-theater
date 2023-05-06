package woowacourse.movie.activity.seat

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.util.alarm.AlarmReceiver
import woowacourse.movie.util.alarm.MovieAlarmManager
import woowacourse.movie.R
import woowacourse.movie.activity.seat.contract.SeatSelectionActivityContract
import woowacourse.movie.activity.seat.contract.presenter.SeatSelectionActivityPresenter
import woowacourse.movie.activity.ticket.TicketActivity
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.database.ReservationRepository
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.seat.mapToDomain
import woowacourse.movie.util.Extensions.intentSerializable
import woowacourse.movie.view.SeatSelectView

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionActivityContract.View {

    override val presenter: SeatSelectionActivityContract.Presenter by lazy {
        SeatSelectionActivityPresenter(
            this,
            ticketCount,
            date,
            time,
            movie,
            ReservationRepository(ReservationDatabase.getDatabase(this)),
        )
    }
    private val date by lazy {
        intent.intentSerializable(DATE_KEY, MovieDateUIModel::class.java)
            ?: MovieDateUIModel.movieDate
    }
    private val time by lazy {
        intent.intentSerializable(TIME_KEY, MovieTimeUIModel::class.java)
            ?: MovieTimeUIModel.movieTime
    }
    private val movie by lazy {
        intent.intentSerializable(MOVIE_KEY, MovieUIModel::class.java) ?: MovieUIModel.movieData
    }
    private val ticketCount by lazy {
        intent.intentSerializable(
            TICKET_KEY,
            TicketCountUIModel::class.java,
        ) ?: TicketCountUIModel(0)
    }
    private val enterBtn by lazy { findViewById<TextView>(R.id.enterBtn) }
    private val movieAlarmManager by lazy { MovieAlarmManager(this) }
    private val alarmReceiver by lazy { AlarmReceiver() }
    private lateinit var seatSelectView: SeatSelectView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(alarmReceiver, IntentFilter(AlarmReceiver.ALARM_CODE))
        setContentView(R.layout.activity_seat_selection)
        setUpState(savedInstanceState)
        presenter.loadMovieTitle(movie.title)
    }

    override fun setUpSeatsView(seats: SeatsUIModel, onSeatClick: (SeatUIModel, Int, Int) -> Unit) {
        seatSelectView = SeatSelectView(
            findViewById(R.id.seat_layout),
            onSeatClick,
            seats.mapToDomain(),
        )
    }

    override fun setMovieTitle(title: String) {
        val movieTtile = findViewById<TextView>(R.id.movie_title)
        movieTtile.text = title
    }

    private fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val stateMap = savedInstanceState.getSerializable(STATE_MAP) as Map<String, Any>
            presenter.onRestoreInstanceState(stateMap)
            presenter.updatePrice(true)
        } else {
            presenter.updatePrice(false)
        }
        presenter.loadSeats()
        presenter.setEnterBtnClickable()
    }

    override fun setEnterBtnColor(color: Int) {
        enterBtn.setBackgroundColor(getColor(color))
    }

    override fun setEnterBtnOnClickListener(listener: (() -> Unit)?) {
        val enterBtn = findViewById<TextView>(R.id.enterBtn)
        enterBtn.setOnClickListener {
            listener?.invoke()
        }
    }

    override fun showBookingDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.seat_dialog_title)
            .setMessage(R.string.seat_dialog_message)
            .setPositiveButton(R.string.seat_dialog_yes) { _, _ ->
                presenter.startTicketActivity()
            }
            .setNegativeButton(R.string.seat_dialog_no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun moveTicketActivity(bookingMovie: BookingMovieUIModel) {
        val intent = Intent(this, TicketActivity::class.java)
        intent.putExtra(BOOKING_MOVIE_KEY, bookingMovie)
        movieAlarmManager.putAlarm(bookingMovie)
        startActivity(intent)
        finish()
    }

    override fun selectSeat(row: Int, col: Int) {
        seatSelectView.seatsView[row][col].setBackgroundColor(getColor(R.color.select_seat))
    }

    override fun unselectSeat(row: Int, col: Int) {
        seatSelectView.seatsView[row][col].setBackgroundColor(getColor(R.color.white))
    }

    override fun showSeatSelectionError() {
        Toast.makeText(this, R.string.seats_size_over_error, Toast.LENGTH_LONG)
            .show()
    }

    override fun setPrice(money: Int) {
        val price = findViewById<TextView>(R.id.ticket_price)
        price.text = getString(R.string.ticket_price_seat_page, money)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val stateMap = mutableMapOf<String, Any>()
        presenter.onSavedInstanceState(stateMap)
        outState.putSerializable(STATE_MAP, HashMap(stateMap))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(alarmReceiver)
    }

    companion object {
        const val TICKET_KEY = "ticket"
        const val MOVIE_KEY = "movie"
        const val DATE_KEY = "movie_date"
        const val TIME_KEY = "movie_time"
        const val BOOKING_MOVIE_KEY = "booking_movie"
        const val SEATS_POSITION = "seats_position"
        const val STATE_MAP = "state_map"
    }
}
