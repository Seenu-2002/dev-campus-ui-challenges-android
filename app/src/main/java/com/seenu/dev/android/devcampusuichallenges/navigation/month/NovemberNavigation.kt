package com.seenu.dev.android.devcampusuichallenges.navigation.month

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.november25.GlobalBlackFridayDealsActivity
import com.seenu.dev.android.november25.screens.CircularStockTracker
import com.seenu.dev.android.november25.screens.GlobalBlackFridayDeals
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
    entry<Route.November3> {
        NovemberTheme { CircularStockTracker() }
    }
    entry<Route.November5> {
        val context = LocalContext.current
        onNavigateBack()
        LaunchedEffect(Unit) {
            val intent = Intent(context, GlobalBlackFridayDealsActivity::class.java)
            context.startActivity(intent)
        }
    }
}