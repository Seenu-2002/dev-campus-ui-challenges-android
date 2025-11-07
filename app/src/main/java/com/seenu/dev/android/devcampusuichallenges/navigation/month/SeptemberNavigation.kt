package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.september25.AccessibleAudioSchedule
import com.seenu.dev.android.september25.ExpandableListScreen
import com.seenu.dev.android.september25.MapChipFilterScreen
import com.seenu.dev.android.september25.MultiStageTimelineScreen
import com.seenu.dev.android.september25.TicketBuilderScreen
import com.seenu.dev.android.september25.theme.SeptemberTheme

@Composable
fun EntryProviderBuilder<NavKey>.SeptemberNavigation() {
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
}