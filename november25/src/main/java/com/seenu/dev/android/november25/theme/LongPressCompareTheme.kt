package com.seenu.dev.android.november25.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TextPrimary3 = Color(0xFF041221)
val TextSecondary3 = Color(0xFF526881)
val Outline3 = Color(0xFFD8E4EA)
val TextDisabled3 = Color(0xFFA8B7BF)
val Brand3 = Color(0xFF4291FF)
val Discount3 = Color(0xFFE6556F)
val Background3 = Color(0xFFFEDF0F6)

val ColorScheme.textDisabled3: Color
    get() = TextDisabled3

val ColorScheme.brand3: Color
    get() = Brand3

val ColorScheme.discount3: Color
    get() = Discount3

val ColorScheme.background3: Color
    get() = Background3

private val lightColorScheme = lightColorScheme(
    background = Background,
    outline = Outline3,
    surface = Surface,
    onPrimaryContainer = TextPrimary3,
    onSecondaryContainer = TextSecondary3,
)

@Composable
fun LongPressGestureTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography(),
        content = content
    )
}