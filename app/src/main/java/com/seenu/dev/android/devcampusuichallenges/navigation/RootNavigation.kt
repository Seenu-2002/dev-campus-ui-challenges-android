package com.seenu.dev.android.devcampusuichallenges.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.seenu.dev.android.devcampusuichallenges.LauncherScreen
import com.seenu.dev.android.devcampusuichallenges.navigation.month.OctoberNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.SeptemberNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.JuneNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.JulyNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.AugustNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.DecemberNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.FebruaryNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.JanuaryNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.month.NovemberNavigation
import com.seenu.dev.android.devcampusuichallenges.state.months

@Composable
fun RootNavigation(modifier: Modifier = Modifier) {
    val backstack = rememberNavBackStack<Route>(Route.ListScreen)
    NavDisplay(
        modifier = modifier,
        backStack = backstack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
        ), entryProvider = entryProvider {

            entry<Route.ListScreen> {
                LauncherScreen(
                    months = months
                ) { challenge ->
                    backstack.add(challenge.route)
                }
            }

            JuneNavigation()
            JulyNavigation()
            AugustNavigation()
            SeptemberNavigation()
            OctoberNavigation()
            NovemberNavigation(backStack = backstack, onNavigateBack = {
                backstack.lastOrNull()?.let {
                    backstack.remove(it)
                }
            })
            DecemberNavigation()
            JanuaryNavigation(backStack = backstack, onNavigateBack = {
                backstack.lastOrNull()?.let {
                    backstack.remove(it)
                }
            })
            FebruaryNavigation(backStack = backstack, onNavigateBack = {
                backstack.lastOrNull()?.let {
                    backstack.remove(it)
                }
            })
        })
}