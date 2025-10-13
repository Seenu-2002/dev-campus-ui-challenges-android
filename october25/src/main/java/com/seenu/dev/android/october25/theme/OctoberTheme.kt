package com.seenu.dev.android.october25.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

val Background = Color(0xFF1A0D39)

val colorScheme = lightColorScheme(
    background = Background
)

@Composable
fun OctoberTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colorScheme, content = content)
}