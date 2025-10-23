package com.seenu.dev.android.october25.state

import com.seenu.dev.android.october25.R

enum class Witch {
    MORGANA,
    SELENE,
    HECATE,
    ELVIRA,
    NYX,
    CIRCE
}

fun Witch.getStringRes(): Int {
    return when (this) {
        Witch.MORGANA -> R.string.morgana
        Witch.SELENE -> R.string.selene
        Witch.HECATE -> R.string.hecate
        Witch.ELVIRA -> R.string.elvira
        Witch.NYX -> R.string.nyx
        Witch.CIRCE -> R.string.circe
    }
}

fun Witch.getImageRes(): Int {
    return when (this) {
        Witch.MORGANA -> R.drawable.fortunr_telling_on_coffee_grounds
        Witch.SELENE -> R.drawable.chiromancy
        Witch.HECATE -> R.drawable.magic_ball
        Witch.ELVIRA -> R.drawable.witch
        Witch.NYX -> R.drawable.tarot
        Witch.CIRCE -> R.drawable.runes
    }
}