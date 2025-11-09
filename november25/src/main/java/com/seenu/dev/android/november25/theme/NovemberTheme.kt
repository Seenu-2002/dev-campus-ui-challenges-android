package com.seenu.dev.android.november25.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.seenu.dev.android.november25.R

val Background = Color(0xFFF6F2ED)
val Surface = Color(0xFFFFFFFF)
val Outline = Color(0xFFDFDDDB)
val OutlineAlt = Color(0x33FFFFFF)
val TextPrimary = Color(0xFF211304)
val TextDisabled = Color(0xFF9A9795)
val TextAlt = Color(0xFFFFFFFF)
val TextOnDiscount = Color(0xB3FFFFFF)
val Discount = Color(0xFF7C1414)
val SnackBar = Color(0xB3211304)

val lightColorScheme = lightColorScheme(
    background = Background,
    outline = Outline,
    surface = Surface,
    onSurface = TextPrimary,
    onBackground = TextPrimary,
)


val ColorScheme.outlineAlt: Color
    get() = OutlineAlt

val ColorScheme.textPrimary: Color
    get() = TextPrimary

val ColorScheme.textDisabled: Color
    get() = TextDisabled

val ColorScheme.textAlt: Color
    get() = TextAlt

val ColorScheme.textOnDiscount: Color
    get() = TextOnDiscount

val ColorScheme.discount: Color
    get() = Discount

val ColorScheme.snackBar: Color
    get() = SnackBar

val HostGrotesk = FontFamily(
    Font(R.font.host_grotesk_regular, weight = FontWeight.Normal),
    Font(R.font.host_grotesk_medium, weight = FontWeight.Medium),
    Font(R.font.host_grotesk_semibold, weight = FontWeight.SemiBold),
    Font(R.font.host_grotesk_bold, weight = FontWeight.Bold),
)

@Composable
fun NovemberTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography(),
        content = content
    )
}