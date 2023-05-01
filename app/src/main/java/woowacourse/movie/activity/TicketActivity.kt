package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import woowacourse.movie.R
import woowacourse.movie.activity.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.seat.mapToDomain
import woowacourse.movie.util.Extensions.intentSerializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        setToolbar()

        val bookingMovie = intent.intentSerializable(BOOKING_MOVIE_KEY, BookingMovieUIModel::class.java)
            ?: BookingMovieUIModel.bookingMovie

        showTicketInfo(
            bookingMovie.movie,
            bookingMovie.date.date,
            bookingMovie.time.time,
        )
        showTicketInfo(
            bookingMovie.ticketCount,
            bookingMovie.seats,
        )
        showTicketPrice(
            bookingMovie.seats,
            bookingMovie.date.date,
            bookingMovie.time.time,
            bookingMovie.ticketCount.numberOfPeople,
        )
    }

    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.ticket_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun formatMovieDateTime(date: LocalDate, time: LocalTime): String {
        val formatDate = date.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val formatTime = time.format(DateTimeFormatter.ofPattern(getString(R.string.time_format)))

        return formatDate.plus(" $formatTime")
    }

    private fun showTicketInfo(movie: MovieUIModel, date: LocalDate, time: LocalTime) {
        val movieTitle = findViewById<TextView>(R.id.ticket_title)
        val movieDate = findViewById<TextView>(R.id.ticket_date)
        movieTitle.text = movie.title
        movieDate.text = formatMovieDateTime(date, time)
    }

    private fun showTicketInfo(ticket: TicketCountUIModel, seats: SeatsUIModel) {
        val numberOfPeople = findViewById<TextView>(R.id.number_of_people)
        numberOfPeople.text =
            getString(R.string.ticket_info, ticket.numberOfPeople, seats.getSeatsPositionToString())
    }

    private fun showTicketPrice(seats: SeatsUIModel, date: LocalDate, time: LocalTime, count: Int) {
        val price = findViewById<TextView>(R.id.ticket_price)
        val totalTicketPrice = seats.mapToDomain().caculateSeatPrice(LocalDateTime.of(date, time))

        price.text = getString(R.string.ticket_price, totalTicketPrice)
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
}
