package woowacourse.movie.view

import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import domain.Seat
import domain.SeatCol
import domain.SeatRow
import domain.Seats
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.dto.seat.SeatColUIModel
import woowacourse.movie.dto.seat.SeatUIModel
import woowacourse.movie.dto.seat.SeatRowUIModel
import woowacourse.movie.mapper.seat.mapToDomain
import woowacourse.movie.mapper.seat.mapToUIModel

class SeatSelectView(
    private val binding: ActivitySeatSelectionBinding,
    val onSeatClick: (SeatUIModel, Int, Int) -> Unit,
    private val seats: Seats,
) {
    var seatsView: List<List<TextView>> = mutableListOf()

    init {
        setSeatsView()
        onSeatClickListener()
    }

    private fun setSeatsView() {
        repeat(TABLE_ROW) { row ->
            val tableRow = TableRow(binding.seat.context)
            tableRow.layoutParams = TableLayout.LayoutParams(0, 0, SEAT_WEIGHT)

            repeat(TABLE_COL) { col ->
                tableRow.addView(getSeat(row + 1, col + 1))
            }
            binding.seat.addView(tableRow)
        }
        bindSeatsView()
    }

    private fun bindSeatsView() {
        seatsView = binding.seat
            .children
            .filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<TextView>().toList() }
            .toList()
    }

    private fun getSeat(row: Int, col: Int): TextView {
        val seat = SeatUIModel(SeatRowUIModel.of(row), SeatColUIModel(col))
        val textView = TextView(binding.root.context)
        textView.text = seat.getString()
        textView.setTextColor(binding.root.context.getColor(seat.row.getColor()))
        textView.gravity = Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SEAT_TEXT_SIZE)
        setSeatColor(seat.mapToDomain(), textView)
        textView.layoutParams =
            TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, SEAT_WEIGHT)

        return textView
    }

    private fun setSeatColor(seat: Seat, textView: TextView) {
        if (seats.containsSeat(seat)) {
            textView.setBackgroundColor(binding.root.context.getColor(R.color.select_seat))
        } else {
            textView.setBackgroundColor(binding.root.context.getColor(R.color.white))
        }
    }

    private fun onSeatClickListener() {
        seatsView.forEachIndexed { rowIndex, rowView ->
            rowView.forEachIndexed { colIndex, textView ->
                textView.setOnClickListener {
                    val seat = Seat(
                        SeatRow(rowIndex + 1),
                        SeatCol(colIndex + 1),
                    )
                    onSeatClick(seat.mapToUIModel(), rowIndex, colIndex)
                }
            }
        }
    }

    companion object {
        private const val TABLE_ROW = 5
        private const val TABLE_COL = 4
        private const val SEAT_TEXT_SIZE = 22F
        private const val SEAT_WEIGHT = 1f
    }
}
