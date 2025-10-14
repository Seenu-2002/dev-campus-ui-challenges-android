package com.seenu.dev.android.october25.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.seenu.dev.android.october25.R

val Background = Color(0xFF1A0D39)
val OutlineInactive = Color(0xFF2C1B53)
val SkeletonToastBg = Color(0xFFFF934A)

val colorScheme = lightColorScheme(
    background = Background
)

val ColorScheme.outlineInactive: Color
    get() = OutlineInactive

val ColorScheme.skeletonToastBg: Color
    get() = SkeletonToastBg

val RoadRage = FontFamily(
    Font(
        R.font.road_rage,
        weight = FontWeight.Normal
    ),
    Font(
        R.font.road_rage,
        weight = FontWeight.Bold
    ),
    Font(
        R.font.road_rage,
        weight = FontWeight.SemiBold
    ),
)

@Composable
fun OctoberTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colorScheme, content = content)
}