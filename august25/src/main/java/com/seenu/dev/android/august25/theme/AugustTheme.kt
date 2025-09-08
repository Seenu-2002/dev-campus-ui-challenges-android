package com.seenu.dev.android.august25.theme

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.seenu.dev.android.august25.R

val Primary = Color(0xFF37B98B)
val TextPrimary = Color(0xFF2E3642)
val TextSecondary = Color(0xFF66707F)
val TextDisabled = Color(0xFFB4BDCA)
val Surface = Color(0xFFF4F6F6)
val SurfaceHigher = Color(0xFFFFFFFF)
val Outline = Color(0xFFFFFFFF)
val SurfaceHighest = Color(0xFFE2E5E9)
val Warning = Color(0xFFFEB43C)
val Error = Color(0xFFF9465A)
val Overload = Color(0xFFA60A36)

val ColorScheme.textPrimary
    @Composable get() = TextPrimary

val ColorScheme.textSecondary
    @Composable get() = TextSecondary

val ColorScheme.textDisabled
    @Composable get() = TextDisabled

val ColorScheme.surfaceHigher
    @Composable get() = SurfaceHigher

val ColorScheme.surfaceHighest
    @Composable get() = SurfaceHighest

val ColorScheme.warning
    @Composable get() = Warning

val ColorScheme.overload
    @Composable get() = Overload

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
            outline = Outline,
            error = Error,
        ),
        typography = Typography(
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            )
            /* Other default text styles to override
            titleLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            ),
            labelSmall = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp
            )
            */
        )
    ) {
        val activity = LocalActivity.current ?: return@MaterialTheme
        val view = LocalView.current
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = true
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = true

        content()
    }
}