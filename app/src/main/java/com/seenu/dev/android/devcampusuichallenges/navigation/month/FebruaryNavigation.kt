package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route

@Composable
fun EntryProviderBuilder<NavKey>.FebruaryNavigation(
    backStack: NavBackStack,
    onNavigateBack: () -> Unit
) {
    entry<Route.February4> {
        key(it) {

        }
    }
}