package com.seenu.dev.android.november25.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TextPrimary2 = Color(0xFF041221)
val TextSecondary2 = Color(0xFF526881)
val Outline2 = Color(0xFFD8E4EA)

private val lightColorScheme = lightColorScheme(
    background = Background,
    outline = Outline2,
    surface = Surface,
    onPrimaryContainer = TextPrimary2,
    onSecondaryContainer = TextSecondary2
)

@Composable
fun StickyBannerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography(),
        content = content
    )
}