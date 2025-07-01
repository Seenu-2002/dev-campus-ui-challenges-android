package com.seenu.dev.android.devcampusuichallenges.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object ListScreen : Route
    @Serializable
    data object June : Route
}