package com.seenu.dev.android.devcampusuichallenges.navigation.month

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.november25.GlobalBlackFridayDealsActivity
import com.seenu.dev.android.november25.screens.CircularStockTracker
import com.seenu.dev.android.november25.screens.HiddenDiscountSwipe
import com.seenu.dev.android.november25.screens.LongPressCompareListScreen
import com.seenu.dev.android.november25.screens.LongPressCompareScreen
import com.seenu.dev.android.november25.screens.StickyAdBanner
import com.seenu.dev.android.november25.state.compareProducts
import com.seenu.dev.android.november25.theme.LongPressGestureTheme
import com.seenu.dev.android.november25.theme.NovemberTheme
import com.seenu.dev.android.november25.theme.StickyBannerTheme

@Composable
fun EntryProviderBuilder<NavKey>.NovemberNavigation(
    backStack: NavBackStack,
    onNavigateBack: () -> Unit
) {
    entry<Route.November1> {
        NovemberTheme { HiddenDiscountSwipe(onNavigateBack = onNavigateBack) }
    }
    entry<Route.November2> {
        StickyBannerTheme { StickyAdBanner() }
    }
    entry<Route.November3> {
        NovemberTheme { CircularStockTracker() }
    }
    entry<Route.November4> {
        LongPressGestureTheme {
            LongPressCompareListScreen(
                onNavigateBack = onNavigateBack,
                onCompare = { productA, productB ->
                    val entry = Route.November4CompareScreen(
                        product1Id = productA,
                        product2Id = productB
                    )
                    backStack.add(entry)
                })
        }
    }
    entry<Route.November4CompareScreen> {
        val entry = backStack.last() as? Route.November4CompareScreen
        entry ?: return@entry
        LongPressGestureTheme {
            val product1 = compareProducts.findLast { it.id == entry.product1Id }!!
            val product2 = compareProducts.findLast { it.id == entry.product2Id }!!
            LongPressCompareScreen(
                productA = product1,
                productB = product2,
                onNavigateBack = onNavigateBack
            )
        }
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