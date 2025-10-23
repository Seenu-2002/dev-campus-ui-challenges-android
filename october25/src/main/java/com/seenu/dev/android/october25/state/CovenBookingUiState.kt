package com.seenu.dev.android.october25.state

sealed class CovenBookingUiState {
    abstract val witch: Witch
    data class SelectWitch constructor(override val witch: Witch) : CovenBookingUiState()
    data class SelectDate constructor(override val witch: Witch) : CovenBookingUiState()
    data class SelectSlot constructor(override val witch: Witch, val date: String) : CovenBookingUiState()
    data class ConfirmBooking constructor(override val witch: Witch, val bookingDetail: BookingDetail) : CovenBookingUiState()
}