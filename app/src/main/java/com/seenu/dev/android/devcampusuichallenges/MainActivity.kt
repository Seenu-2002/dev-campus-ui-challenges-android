package com.seenu.dev.android.devcampusuichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.seenu.dev.android.august25.screens.OrderQueueOutpostScreen
import com.seenu.dev.android.august25.screens.ThermometerTreckScreen
import com.seenu.dev.android.august25.theme.AugustTheme
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.devcampusuichallenges.state.months
import com.seenu.dev.android.devcampusuichallenges.ui.theme.DevCampusUIChallengesTheme
import com.seenu.dev.android.july25.BottomNavigationWithUnreadBadgesScreen
import com.seenu.dev.android.july25.EmojiReactionBubbleScreen
import com.seenu.dev.android.july25.MessageCardScreen
import com.seenu.dev.android.june25.BirthdayInviteCardScreen
import com.seenu.dev.android.october25.CursedCountdown
import com.seenu.dev.android.october25.HauntedThemeSwitcher
import com.seenu.dev.android.october25.theme.OctoberTheme
import com.seenu.dev.android.september25.AccessibleAudioSchedule
import com.seenu.dev.android.september25.ExpandableListScreen
import com.seenu.dev.android.september25.MapChipFilterScreen
import com.seenu.dev.android.september25.MultiStageTimelineScreen
import com.seenu.dev.android.september25.TicketBuilderScreen
import com.seenu.dev.android.september25.theme.SeptemberTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val SPLASH_DURATION = 5700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        val startTime = System.currentTimeMillis()
//        val splash = installSplashScreen()
//        splash.setKeepOnScreenCondition {
//            System.currentTimeMillis() - startTime < SPLASH_DURATION
//        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backstack = rememberNavBackStack<Route>(Route.ListScreen)
            DevCampusUIChallengesTheme {
                NavDisplay(backStack = backstack, entryProvider = entryProvider<NavKey> {
                    entry<Route.ListScreen> {
                        LauncherScreen(
                            months = months
                        ) { challenge ->
                            backstack.add(challenge.route)
                        }
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

                    entry<Route.August1> { entry ->
                        AugustTheme { ThermometerTreckScreen() }
                    }

                    entry<Route.August2> { entry ->
                        AugustTheme { OrderQueueOutpostScreen() }
                    }

                    entry<Route.September1> { _ ->
                        SeptemberTheme {
                            Surface {
                                ExpandableListScreen()
                            }
                        }
                    }

                    entry<Route.September2> {
                        SeptemberTheme {
                            Surface {
                                TicketBuilderScreen()
                            }
                        }
                    }

                    entry<Route.September3> {
                        SeptemberTheme {
                            Surface {
                                MapChipFilterScreen()
                            }
                        }
                    }

                    entry<Route.September4> {
                        SeptemberTheme {
                            Surface {
                                AccessibleAudioSchedule()
                            }
                        }
                    }

                    entry<Route.September5> {
                        SeptemberTheme {
                            Surface {
                                MultiStageTimelineScreen()
                            }
                        }
                    }

                    entry<Route.October3> {
                        OctoberTheme {
                            Surface {
                                CursedCountdown()
                            }
                        }
                    }

                    entry<Route.October2> {
                        OctoberTheme {
                            Surface {
                                HauntedThemeSwitcher()
                            }
                        }
                    }
                })
            }
        }
    }
}
