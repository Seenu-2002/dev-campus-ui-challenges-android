package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.october25.CovenBookingDesk
import com.seenu.dev.android.october25.CursedCountdown
import com.seenu.dev.android.october25.HalloweenSkeletonPuzzle
import com.seenu.dev.android.october25.HauntedThemeSwitcher
import com.seenu.dev.android.october25.theme.OctoberTheme

@Composable
fun EntryProviderBuilder<NavKey>.OctoberNavigation() {
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

    entry<Route.October4> {
        OctoberTheme {
            Surface {
                CovenBookingDesk()
            }
        }
    }

    entry<Route.October5> {
        OctoberTheme {
            Surface {
                HalloweenSkeletonPuzzle()
            }
        }
    }
}