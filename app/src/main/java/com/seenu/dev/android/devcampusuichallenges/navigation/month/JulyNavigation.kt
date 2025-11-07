package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.july25.BottomNavigationWithUnreadBadgesScreen
import com.seenu.dev.android.july25.EmojiReactionBubbleScreen
import com.seenu.dev.android.july25.MessageCardScreen

@Composable
fun EntryProviderBuilder<NavKey>.JulyNavigation() {
    entry<Route.July> { entry ->
        MessageCardScreen()
    }

    entry<Route.July2> { entry ->
        BottomNavigationWithUnreadBadgesScreen()
    }

    entry<Route.July3> { entry ->
        EmojiReactionBubbleScreen()
    }
}