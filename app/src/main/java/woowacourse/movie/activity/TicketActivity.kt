package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import woowacourse.movie.R
import woowacourse.movie.activity.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.dto.seat.SeatsDto
import woowacourse.movie.dto.ticket.TicketCountDto
import woowacourse.movie.mapper.seat.mapToSeats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        setToolbar()

        val bookingMovie = intent.getSerializableExtra(BOOKING_MOVIE_KEY) as BookingMovieDto

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

    private fun showTicketInfo(movie: MovieDto, date: LocalDate, time: LocalTime) {
        val movieTitle = findViewById<TextView>(R.id.ticket_title)
        val movieDate = findViewById<TextView>(R.id.ticket_date)
        movieTitle.text = movie.title
        movieDate.text = formatMovieDateTime(date, time)
    }

    private fun showTicketInfo(ticket: TicketCountDto, seats: SeatsDto) {
        val numberOfPeople = findViewById<TextView>(R.id.number_of_people)
        numberOfPeople.text =
            getString(R.string.ticket_info, ticket.numberOfPeople, seats.getSeatsPositionToString())
    }

    private fun showTicketPrice(seats: SeatsDto, date: LocalDate, time: LocalTime, count: Int) {
        val price = findViewById<TextView>(R.id.ticket_price)
        val totalTicketPrice = seats.mapToSeats().caculateSeatPrice(LocalDateTime.of(date, time))

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
