package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.august25.screens.OrderQueueOutpostScreen
import com.seenu.dev.android.august25.screens.ParcelPigeonRaceScreen
import com.seenu.dev.android.august25.screens.ThermometerTreckScreen
import com.seenu.dev.android.august25.theme.AugustTheme
import com.seenu.dev.android.devcampusuichallenges.navigation.Route

@Composable
fun EntryProviderBuilder<NavKey>.AugustNavigation() {
    entry<Route.August1> { entry ->
        AugustTheme { ThermometerTreckScreen() }
    }

    entry<Route.August2> { entry ->
        AugustTheme { OrderQueueOutpostScreen() }
    }

    entry<Route.August3> { entry ->
        AugustTheme { ParcelPigeonRaceScreen() }
    }
}