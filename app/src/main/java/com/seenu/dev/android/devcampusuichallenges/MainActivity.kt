package com.seenu.dev.android.devcampusuichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.seenu.dev.android.devcampusuichallenges.june.BirthdayInviteCardScreen
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.devcampusuichallenges.state.Challenge
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
                })
            }
        }
    }
}
