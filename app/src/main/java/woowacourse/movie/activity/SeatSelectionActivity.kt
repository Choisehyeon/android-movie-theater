package woowacourse.movie.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import domain.Seat
import domain.Seats
import woowacourse.movie.AlarmReceiver
import woowacourse.movie.MovieAlarmManager
import woowacourse.movie.R
import woowacourse.movie.dto.BookingHistoryUIModel
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.seat.mapToDomain
import woowacourse.movie.mapper.seat.mapToUIModel
import woowacourse.movie.util.Extensions.intentSerializable
import woowacourse.movie.view.SeatSelectView
import java.time.LocalDateTime

class SeatSelectionActivity : AppCompatActivity() {

    private var seats = Seats()

    private val date by lazy { intent.intentSerializable(DATE_KEY, MovieDateUIModel::class.java) ?: MovieDateUIModel.movieDate }
    private val time by lazy { intent.intentSerializable(TIME_KEY, MovieTimeUIModel::class.java) ?: MovieTimeUIModel.movieTime  }
    private val movie by lazy {  intent.intentSerializable(MOVIE_KEY, MovieUIModel::class.java) ?: MovieUIModel.movieData}
    private val ticketCount by lazy { intent.intentSerializable(TICKET_KEY, TicketCountUIModel::class.java) ?: TicketCountUIModel(0) }
    private val enterBtn by lazy { findViewById<TextView>(R.id.enterBtn) }
    private val movieAlarmManager by lazy { MovieAlarmManager(this) }
    private val alarmReceiver by lazy { AlarmReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(alarmReceiver, IntentFilter(AlarmReceiver.ALARM_CODE))

        setContentView(R.layout.activity_seat_selection)
        setUpState(savedInstanceState)
        setMovieTitle()
    }

    private fun setUpSeatsView() {
        SeatSelectView(
            findViewById(R.id.seat_layout),
            ::onSeatClick,
            seats,
        )
    }

    private fun setMovieTitle() {
        val movieTtile = findViewById<TextView>(R.id.movie_title)
        movieTtile.text = movie.title
    }

    private fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val seatsDto = savedInstanceState.getSerializable(SEATS_POSITION) as SeatsUIModel
            seats = seatsDto.mapToDomain()
            setPrice(seats.caculateSeatPrice(LocalDateTime.of(date.date, time.time)))
        } else {
            setPrice(0)
        }
        setUpSeatsView()
        setEnterBtnClickable()
    }

    private fun onSeatClick(seat: Seat, textView: TextView) {
        when {
            isPossibleSelect(seat, ticketCount.numberOfPeople) -> selectSeat(
                textView,
                seat,
            )
            isSeatCancelable(seat) -> unselectSeat(textView, seat)
            else -> {
                Toast.makeText(this, R.string.seats_size_over_error, Toast.LENGTH_LONG)
                    .show()
                return
            }
        }
        setPrice(seats.caculateSeatPrice(LocalDateTime.of(date.date, time.time)))
        setEnterBtnClickable()
    }

    private fun setEnterBtnClickable() {
        if (isPossibleEnter(ticketCount.numberOfPeople)) {
            enterBtn.setBackgroundColor(getColor(R.color.enter))
            onEnterBtnClickListener(enterBtn)
        } else {
            enterBtn.setBackgroundColor(getColor(R.color.not_enter))
            enterBtn.setOnClickListener(null)
        }
    }

    private fun onEnterBtnClickListener(enterBtn: TextView) {
        enterBtn.setOnClickListener {
            showBookingDialog()
        }
    }

    private fun showBookingDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.seat_dialog_title)
            .setMessage(R.string.seat_dialog_message)
            .setPositiveButton(R.string.seat_dialog_yes) { _, _ -> moveActivity() }
            .setNegativeButton(R.string.seat_dialog_no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun isSeatCancelable(seat: Seat): Boolean {
        return seats.containsSeat(seat)
    }

    private fun isPossibleEnter(count: Int): Boolean {
        return seats.checkSeatSizeMatch(count)
    }

    private fun isPossibleSelect(seat: Seat, count: Int): Boolean {
        return !seats.containsSeat(seat) && seats.isPossibleSeatSize(count)
    }

    private fun selectSeat(textView: TextView, seat: Seat) {
        textView.setBackgroundColor(getColor(R.color.select_seat))
        seats.add(seat)
    }

    private fun unselectSeat(textView: TextView, seat: Seat) {
        textView.setBackgroundColor(getColor(R.color.white))
        seats.remove(seat)
    }

    private fun setPrice(ticketPrice: Int) {
        val price = findViewById<TextView>(R.id.ticket_price)
        price.text = getString(R.string.ticket_price_seat_page, ticketPrice)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(SEATS_POSITION, seats.mapToUIModel())
        super.onSaveInstanceState(outState)
    }

    private fun moveActivity() {
        val bookingMovie = BookingMovieUIModel(movie, date, time, ticketCount, seats.mapToUIModel())
        val intent = Intent(this, TicketActivity::class.java)
        intent.putExtra(BOOKING_MOVIE_KEY, bookingMovie)
        movieAlarmManager.putAlarm(bookingMovie)
        BookingHistoryUIModel.add(bookingMovie)
        startActivity(intent)
        finish()
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
    }
}
