package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.december25.theme.DecemberTheme
import com.seenu.dev.android.december25.SantaClapPiano
import com.seenu.dev.android.devcampusuichallenges.navigation.Route

@Composable
fun EntryProviderBuilder<NavKey>.DecemberNavigation() {
    entry<Route.December2> {
        DecemberTheme {
            SantaClapPiano()
        }
    }
}