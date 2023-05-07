package woowacourse.movie.activity.ticket

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.activity.ticket.contract.TicketActivityContract
import woowacourse.movie.activity.ticket.contract.presenter.TicketActivityPresenter
import woowacourse.movie.activity.seat.SeatSelectionActivity
import woowacourse.movie.databinding.ActivityTicketBinding
import woowacourse.movie.dto.movie.BookingMovieUIModel
import woowacourse.movie.dto.movie.MovieDateUIModel
import woowacourse.movie.dto.movie.MovieTimeUIModel
import woowacourse.movie.dto.seat.SeatsUIModel
import woowacourse.movie.dto.ticket.TicketCountUIModel
import woowacourse.movie.mapper.seat.mapToDomain
import woowacourse.movie.util.Extensions.intentSerializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TicketActivity : AppCompatActivity(), TicketActivityContract.View {

    override val presenter: TicketActivityContract.Presenter by lazy { TicketActivityPresenter(this) }
    private lateinit var binding: ActivityTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket)
        setToolbar()
        val bookingMovie =
            intent.intentSerializable(
                SeatSelectionActivity.BOOKING_MOVIE_KEY,
                BookingMovieUIModel::class.java,
            )
                ?: BookingMovieUIModel.bookingMovie
        presenter.loadData(bookingMovie)
    }

    override fun setToolbar() {
        setSupportActionBar(binding.ticketToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun formatMovieDateTime(date: LocalDate, time: LocalTime): String {
        val formatDate = date.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val formatTime = time.format(DateTimeFormatter.ofPattern(getString(R.string.time_format)))

        return formatDate.plus(" $formatTime")
    }

    override fun showTicketMovieInfo(
        title: String,
        date: MovieDateUIModel,
        time: MovieTimeUIModel,
    ) {
        binding.ticketTitle.text = title
        binding.ticketDate.text = formatMovieDateTime(date.date, time.time)
    }

    override fun showTicketInfo(ticket: TicketCountUIModel, seats: SeatsUIModel, theaterName: String) {
        binding.numberOfPeople.text =
            getString(R.string.ticket_info, ticket.numberOfPeople, seats.getSeatsPositionToString(), theaterName)
    }

    override fun showTicketPrice(
        seats: SeatsUIModel,
        date: MovieDateUIModel,
        time: MovieTimeUIModel,
    ) {
        val totalTicketPrice =
            seats.mapToDomain().caculateSeatPrice(LocalDateTime.of(date.date, time.time))

        binding.ticketPrice.text = getString(R.string.ticket_price, totalTicketPrice)
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
