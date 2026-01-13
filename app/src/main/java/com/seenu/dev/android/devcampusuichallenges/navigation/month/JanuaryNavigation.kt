package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.january26.WinterTravelGallery
import com.seenu.dev.android.january26.WinterTravelGalleryDetail
import com.seenu.dev.android.january26.theme.JanuaryTheme

@Composable
fun EntryProviderBuilder<NavKey>.JanuaryNavigation(
    backStack: NavBackStack,
    onNavigateBack: () -> Unit
) {
    entry<Route.January1> {
        JanuaryTheme {
            WinterTravelGallery(openDestination = {
                backStack.add(Route.January1_1(it))
            })
        }
    }

    entry<Route.January1_1> {
        JanuaryTheme {
            WinterTravelGalleryDetail(
                destination = it.destination,
                onBack = onNavigateBack
            )
        }
    }
}