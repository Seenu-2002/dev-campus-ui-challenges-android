package com.seenu.dev.android.devcampusuichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.seenu.dev.android.devcampusuichallenges.navigation.RootNavigation
import com.seenu.dev.android.devcampusuichallenges.ui.theme.DevCampusUIChallengesTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val SPLASH_DURATION = 5700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevCampusUIChallengesTheme {
                RootNavigation()
            }
        }
    }
}
