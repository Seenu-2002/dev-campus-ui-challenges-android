package com.seenu.dev.android.september25.theme

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.seenu.dev.android.september25.R

val Surface = Color(0xFFFFFFF0)
val SurfaceHigher = Color(0xFFFCF3E2)
val TextPrimary = Color(0xFF421E17)
val TextSecondary = Color(0xFF786B68)
val TextDisabled = Color(0xFFA99E9C)
val Lime = Color(0xFFE0E270)
val Orange = Color(0xFFEC9C50)
val Pink = Color(0xFFF59BB0)

val ColorScheme.surfaceHigher
    @Composable get() = SurfaceHigher

val ColorScheme.textPrimary
    @Composable get() = TextPrimary

val ColorScheme.textSecondary
    @Composable get() = TextSecondary

val ColorScheme.textDisabled
    @Composable get() = TextDisabled

val ColorScheme.lime
    @Composable get() = Lime

val ColorScheme.orange
    @Composable get() = Orange

val ColorScheme.pink
    @Composable get() = Pink

val Parkinsans = FontFamily(
    Font(R.font.parkinsans_regular, weight = FontWeight.Normal),
    Font(R.font.parkinsans_medium, weight = FontWeight.Medium),
    Font(R.font.parkinsans_semibold, weight = FontWeight.SemiBold),
    Font(R.font.parkinsans_bold, weight = FontWeight.Bold),
)

@Composable
fun SeptemberTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            surface = Surface,
            onSurface = TextPrimary
        ),
        typography = Typography()
    ) {
        LocalActivity.current?.let { activity ->
            val view = LocalView.current
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = true
        }
        content()
    }
}