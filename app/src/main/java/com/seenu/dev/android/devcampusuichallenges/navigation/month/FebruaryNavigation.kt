package com.seenu.dev.android.devcampusuichallenges.navigation.month

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.february26.shared_valentine.SharedValentine
import com.seenu.dev.android.february26.shared_valentine.SharedValentineTheme
import com.seenu.dev.android.february26.shared_valentine.receiver.SharedValentineReceiver
import com.seenu.dev.android.february26.shared_valentine.sender.SharedValentineSender

@Composable
fun EntryProviderBuilder<NavKey>.FebruaryNavigation(
    backStack: NavBackStack,
    onNavigateBack: () -> Unit
) {
    entry<Route.SharedValentineTypeChooser> {
        key(it) {
            SharedValentineTheme {
                SharedValentine(
                    openSender = {
                        backStack.removeLast()
                        backStack.add(Route.SharedValentineSender)
                    },
                    openReceiver = {
                        backStack.removeLast()
                        backStack.add(Route.SharedValentineReceiver(null))
                    }
                )
            }
        }
    }
    entry<Route.SharedValentineSender> {
        key(it) {
            SharedValentineTheme { SharedValentineSender() }
        }
    }
    entry<Route.SharedValentineReceiver> {
        key(it) {
            var item by remember {
                mutableStateOf(it.receivedGift)
            }
            SharedValentineTheme {
                SharedValentineReceiver(item, onReceiveHandled = {
                    item = null
                })
            }
        }
    }
}