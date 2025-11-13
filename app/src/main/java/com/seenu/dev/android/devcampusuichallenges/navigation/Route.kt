package com.seenu.dev.android.devcampusuichallenges.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object ListScreen : Route
    @Serializable
    data object June : Route

    @Serializable
    data object July : Route

    @Serializable
    data object July2 : Route

    @Serializable
    data object July3 : Route

    @Serializable
    data object August1 : Route

    @Serializable
    data object August2 : Route

    @Serializable
    data object August3 : Route

    @Serializable
    data object September1 : Route

    @Serializable
    data object September2 : Route

    @Serializable
    data object September3 : Route

    @Serializable
    data object September4 : Route

    @Serializable
    data object September5 : Route

    @Serializable
    data object October2 : Route

    @Serializable
    data object October3 : Route

    @Serializable
    data object October4 : Route

    @Serializable
    data object October5 : Route

    @Serializable
    data object November1 : Route

    @Serializable
    data object November2 : Route

    @Serializable
    data object November3 : Route

    @Serializable
    data object November5 : Route
}