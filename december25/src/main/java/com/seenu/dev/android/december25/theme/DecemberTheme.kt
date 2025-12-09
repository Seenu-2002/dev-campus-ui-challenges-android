package com.seenu.dev.android.december25.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.seenu.dev.android.december25.R

val Background = Color(0xFFDFE0DF)
val Title = Color(0xFF00369A)
val KeyHoverGradientStart = Color(0xFFD0FFFD)
val KeyHoverGradientEnd = Color(0xFF88B5F4)

val ColorScheme.title: Color
    get() = Title

val ColorScheme.keyHoverGradientStart: Color
    get() = KeyHoverGradientStart

val ColorScheme.keyHoverGradientEnd: Color
    get() = KeyHoverGradientEnd

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

@Composable
fun DecemberTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Background
        ),
        content = content
    )
}