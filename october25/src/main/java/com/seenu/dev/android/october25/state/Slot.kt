package com.seenu.dev.android.october25.state

data class Slot constructor(
    val time: String,
    val status: SlotStatus
)

sealed interface SlotStatus {
    data object Available : SlotStatus
    data class Reserved constructor(
        val by: Witch
    ) : SlotStatus
}