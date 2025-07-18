package com.seenu.dev.android.devcampusuichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.seenu.dev.android.devcampusuichallenges.july.BottomNavigationWithUnreadBadgesScreen
import com.seenu.dev.android.devcampusuichallenges.july.EmojiReactionBubbleScreen
import com.seenu.dev.android.devcampusuichallenges.july.MessageCardScreen
import com.seenu.dev.android.devcampusuichallenges.june.BirthdayInviteCardScreen
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.devcampusuichallenges.ui.theme.DevCampusUIChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backstack = rememberNavBackStack<Route>(Route.ListScreen)
            DevCampusUIChallengesTheme {
                NavDisplay(backStack = backstack, entryProvider = entryProvider<NavKey> {
                    entry<Route.ListScreen> {
                        ListScreen(onChallengeSelected = { challenge ->
                            backstack.add(challenge.route)
                        })
                    }

                    entry<Route.June> { entry ->
                        BirthdayInviteCardScreen()
                    }

                    entry<Route.July> { entry ->
                        MessageCardScreen()
                    }

                    entry<Route.July2> { entry ->
                        BottomNavigationWithUnreadBadgesScreen()
                    }

                    entry<Route.July3> { entry ->
                        EmojiReactionBubbleScreen()
                    }
                })
            }
        }
    }
}
