package com.seenu.dev.android.february26

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val StackSans = FontFamily(
    Font(R.font.stack_sans_regular, weight = FontWeight.Normal),
    Font(R.font.stack_sans_medium, weight = FontWeight.Medium),
    Font(R.font.stack_sans_semibold, weight = FontWeight.SemiBold),
    Font(R.font.stack_sans_bold, weight = FontWeight.Bold),
)

private val Background = Color(0xFFFFFFFF)
private val Surface = Color(0xFFF1F4F6)
private val SurfaceActive = Color(0xFFFFC3CD)
private val OutlineActive = Color(0x4DFFC3CD)
private val TextPrimary = Color(0xFF071121)
private val TextSecondary = Color(0xFF47566E)
private val ButtonPrimary = Color(0xFF6A1A2E)
private val ButtonPrimaryDisabled = Color(0xFFD0D8DE)

val ColorScheme.surfaceActive: Color
    get() = SurfaceActive

val ColorScheme.buttonPrimaryDisabled: Color
    get() = ButtonPrimaryDisabled

@Composable
fun SharedValentineTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(
        primary = ButtonPrimary,
        onPrimary = TextPrimary,
        onSecondary = TextSecondary,
        outline = OutlineActive,
        background = Background,
        surface = Surface,
    ), content = content)
}