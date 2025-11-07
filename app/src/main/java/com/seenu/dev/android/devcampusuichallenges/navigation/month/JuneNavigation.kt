package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.june25.BirthdayInviteCardScreen

@Composable
fun EntryProviderBuilder<NavKey>.JuneNavigation() {
    entry<Route.June> { entry ->
        BirthdayInviteCardScreen()
    }
}