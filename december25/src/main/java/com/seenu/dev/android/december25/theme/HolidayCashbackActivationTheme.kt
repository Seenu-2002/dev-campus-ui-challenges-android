package com.seenu.dev.android.december25.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val ButtonPrimary = Color(0xFFAD3A25)
val ButtonPrimaryDisabled = Color(0xFFFFBFB4)
val TextPrimaryDark = Color(0xFF2C1F04)
val TextPrimaryWhite = Color.White
val TextSecondary = Color(0xFFBAB4AD)
val SurfaceBg = Color(0xFFFFFAF0)

val ColorScheme.buttonPrimary: Color
    get() = ButtonPrimary

val ColorScheme.buttonPrimaryDisabled: Color
    get() = ButtonPrimaryDisabled

val ColorScheme.textPrimaryDark: Color
    get() = TextPrimaryDark

val ColorScheme.textPrimaryWhite: Color
    get() = TextPrimaryWhite

val ColorScheme.textSecondary: Color
    get() = TextSecondary

val ColorScheme.surfaceBg: Color
    get() = SurfaceBg
