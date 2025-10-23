package com.seenu.dev.android.october25.viewmodel

import androidx.lifecycle.ViewModel
import com.seenu.dev.android.october25.state.BookingDetail
import com.seenu.dev.android.october25.state.CovenBookingUiState
import com.seenu.dev.android.october25.state.Slot
import com.seenu.dev.android.october25.state.SlotStatus
import com.seenu.dev.android.october25.state.Witch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CovenBookingViewModel : ViewModel() {

    private val _bookingsUiState: MutableStateFlow<CovenBookingUiState?> = MutableStateFlow(null)
    val bookingsUiState: StateFlow<CovenBookingUiState?> = _bookingsUiState.asStateFlow()

    private val bookings = mutableMapOf<Witch, BookingDetail>()
    private val bookingsByDate = mutableMapOf<String, HashSet<BookingDetail>>()

    init {
        feedInitialData()
    }

    fun getBookingDetail(witch: Witch): BookingDetail? {
        return bookings[witch]
    }

    fun selectWitch(witch: Witch?) {
        _bookingsUiState.value = witch?.let { CovenBookingUiState.SelectWitch(it) }
    }

    fun confirmWitch(witch: Witch) {
        _bookingsUiState.value = CovenBookingUiState.SelectDate(witch)
    }

    fun selectDate(witch: Witch, date: String) {
        _bookingsUiState.value = CovenBookingUiState.SelectSlot(witch, date)
    }

    fun selectSlot(witch: Witch, date: String, slot: Slot) {
        val bookingDetail = BookingDetail(witch, date, slot)
        _bookingsUiState.value = CovenBookingUiState.ConfirmBooking(witch, bookingDetail)
    }

    fun confirmBooking(bookingDetail: BookingDetail) {
        bookings[bookingDetail.witch] = bookingDetail
        if (bookingsByDate[bookingDetail.date] == null) {
            bookingsByDate[bookingDetail.date] = hashSetOf()
        }
        bookingsByDate[bookingDetail.date]!!.add(bookingDetail)
        _bookingsUiState.value = null
    }

    private val timeSlots = listOf(
        "23:00",
        "00:00",
        "01:00",
        "02:00",
        "03:00",
        "04:00",
        "05:00",
    )

    fun getSlots(date: String): List<Slot> {
        val existingBooking = bookingsByDate[date] ?: emptySet()
        return timeSlots.map { time ->
            if (existingBooking.any { it.slot.time == time }) {
                val existingBooking = existingBooking.first { it.slot.time == time }
                Slot(
                    time,
                    SlotStatus.Reserved(existingBooking.witch)
                )
            } else {
                Slot(time, SlotStatus.Available)
            }
        }
    }

    fun clearSelection() {
        _bookingsUiState.value = null
    }

    private fun feedInitialData() {
        val data = listOf(
            BookingDetail(
                witch = Witch.SELENE,
                date = "OCT 30",
                slot = Slot(
                    time = "00:00",
                    status = SlotStatus.Available
                )
            ),
            BookingDetail(
                witch = Witch.MORGANA,
                date = "OCT 31",
                slot = Slot(
                    time = "02:00",
                    status = SlotStatus.Available
                )
            ), BookingDetail(
                witch = Witch.ELVIRA,
                date = "NOV 1",
                slot = Slot(
                    time = "03:00",
                    status = SlotStatus.Available
                )
            ),
            BookingDetail(
                witch = Witch.HECATE,
                date = "OCT 30",
                slot = Slot(
                    time = "04:00",
                    status = SlotStatus.Available
                )
            )
        )
        for (booking in data) {
            confirmBooking(booking)
        }
    }

}
