package com.seenu.dev.android.devcampusuichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.seenu.dev.android.devcampusuichallenges.navigation.RootNavigation
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.devcampusuichallenges.ui.theme.DevCampusUIChallengesTheme
import com.seenu.dev.android.february26.shared_valentine.components.ValentineGift

class MainActivity : ComponentActivity() {

    companion object {
        private const val SPLASH_DURATION = 5700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isOpenedFromCustomAction =
            intent?.action == "com.seenu.dev.android.devcampusuichallenges.RECEIVE_VALENTINE_GIFT"
        val routes = if (isOpenedFromCustomAction) {
            val gift = intent?.getStringExtra("gift")
            if (gift == null) {
                emptyList()
            } else {
                listOf(
                    Route.ListScreen,
                    Route.SharedValentineReceiver(ValentineGift.valueOf(gift.uppercase()))
                )
            }
        } else {
            emptyList()
        }
        setContent {
            DevCampusUIChallengesTheme {
                RootNavigation(routes)
            }
        }
    }
}
