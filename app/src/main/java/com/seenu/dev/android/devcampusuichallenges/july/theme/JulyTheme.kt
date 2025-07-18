package com.seenu.dev.android.devcampusuichallenges.july.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.seenu.dev.android.devcampusuichallenges.R
import com.seenu.dev.android.devcampusuichallenges.ui.theme.DefaultTypography

val Primary = Color(0xFF75FABF)
val Surface30 = Color(0x4D1F222A)
val Surface50 = Color(0x801F222A)
val Background = Color(0xFF06060A)
val OnSurface = Color(0xFFFFFFFF)
val OnSurfaceVariant = Color(0xFFAFB2B9)
val OnSurfaceAlt = Color(0xFF0F0F18)
val Blue = Color(0xFF68C3FF)
val SurfaceAlt30 = Color(0x4DFFFFFF)
val Error = Color(0xFFF83468)
val BackgroundGradientEnd = Color(0xFF0F1318)
val Blue12 = Color(0x1F68C3FF)

val ColorScheme.onSurfaceAlt
    @Composable get() = OnSurfaceAlt

val ColorScheme.surfaceAlt30
    @Composable get() = SurfaceAlt30

val ColorScheme.blue
    @Composable get() = Blue

val ColorScheme.surface50
    @Composable get() = Surface50

val ColorScheme.surface30
    @Composable get() = Surface30

val ColorScheme.backgroundGradientEnd
    @Composable get() = BackgroundGradientEnd

val ColorScheme.blue12
    @Composable get() = Blue12

val Urbanist = FontFamily(
    Font(R.font.urbanist_regular, weight = FontWeight.Normal),
    Font(R.font.urbanist_medium, weight = FontWeight.Medium),
    Font(R.font.urbanist_semibold, weight = FontWeight.SemiBold),
    Font(R.font.urbanist_bold, weight = FontWeight.Bold),
)

@Composable
fun JulyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = Primary,
            background = Background,
            onSurface = OnSurface,
            onSurfaceVariant = OnSurfaceVariant,
            error = Error
        ),
        typography = DefaultTypography,
        content = content
    )
}