package com.seenu.dev.android.october25.state

data class BookingDetail constructor(
    val witch: Witch,
    val date: String,
    val slot: Slot
) {
    val dateWithTime: String
        get() = "$date, ${slot.time}"
}