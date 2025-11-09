package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.november25.screens.HiddenDiscountSwipe
import com.seenu.dev.android.november25.screens.StickyAdBanner
import com.seenu.dev.android.november25.theme.NovemberTheme
import com.seenu.dev.android.november25.theme.StickyBannerTheme

@Composable
fun EntryProviderBuilder<NavKey>.NovemberNavigation(onNavigateBack: () -> Unit) {
    entry<Route.November1> {
        NovemberTheme { HiddenDiscountSwipe(onNavigateBack = onNavigateBack) }
    }
    entry<Route.November2> {
        StickyBannerTheme { StickyAdBanner() }
    }
}