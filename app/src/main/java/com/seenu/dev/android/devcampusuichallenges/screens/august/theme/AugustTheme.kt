package com.seenu.dev.android.devcampusuichallenges.screens.august.theme

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.seenu.dev.android.devcampusuichallenges.R
import com.seenu.dev.android.devcampusuichallenges.ui.theme.DefaultTypography

val Primary = Color(0xFF37B98B)
val TextPrimary = Color(0xFF2E3642)
val TextSecondary = Color(0xFF66707F)
val TextDisabled = Color(0xFFB4BDCA)
val Surface = Color(0xFFF4F6F6)
val SurfaceHigher = Color(0xFFFFFFFF)
val Outline = Color(0xFFFFFFFF)

val ColorScheme.textPrimary
    @Composable get() = TextPrimary

val ColorScheme.textSecondary
    @Composable get() = TextSecondary

val ColorScheme.textDisabled
    @Composable get() = TextDisabled

val ColorScheme.surfaceHigher
    @Composable get() = SurfaceHigher

val HostGrotesk = FontFamily(
    Font(R.font.host_grotesk_regular, weight = FontWeight.Normal),
    Font(R.font.host_grotesk_medium, weight = FontWeight.Medium),
    Font(R.font.host_grotesk_semibold, weight = FontWeight.SemiBold),
    Font(R.font.host_grotesk_bold, weight = FontWeight.Bold),
)

@Composable
fun AugustTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = Primary,
            surface = Surface,
            outline = Outline
        ),
        typography = DefaultTypography,
    ) {
        val activity = LocalActivity.current ?: return@MaterialTheme
        val view = LocalView.current
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = true
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = true

        content()
    }
}