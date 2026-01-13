package com.seenu.dev.android.december25.state

import androidx.compose.ui.graphics.Color

enum class PopupOptions {
    FONT, TEXT_COLOR, FONT_SIZE
}

enum class Theme {
    GOLDEN_EVE,
    SNOWY_WHITE,
    EVERGREEN_WISH,
    FROSTY_LIGHT
}

enum class Font {
    PT_SANS,
    MONTSERRAT,
}

enum class TextColor constructor(val background: Color, val border: Color?) {
    COLOR_DEFAULT(Color(0xFFFFFFFF), null),
    COLOR_1(Color(0xFFFFFFFF), Color(0xFFBFC1E2)),
    COLOR_2(Color(0xFFFFF2E9), null),
    COLOR_3(Color(0xFFF7E6C7), null),
    COLOR_4(Color(0xFF943112), null),
    COLOR_5(Color(0xFF0D1040), null),
}

enum class FontSize {
    SMALL, MEDIUM, LARGE
}

data class ScreenColors constructor(
    val defaultTextColor: Color,
    val borderColor: Color?,
)

fun Theme.getScreenColors(): ScreenColors {
    return when (this) {
        Theme.GOLDEN_EVE -> ScreenColors(
            defaultTextColor = Color(0xFFF7E6C7),
            borderColor = null
        )

        Theme.SNOWY_WHITE -> ScreenColors(
            defaultTextColor = Color(0xFFFFFFFF),
            borderColor = Color(0xFFFFFFFF)
        )

        Theme.EVERGREEN_WISH -> ScreenColors(
            defaultTextColor = Color(0xFFF7E6C7),
            borderColor = Color(0xFFFFFFFF)
        )

        Theme.FROSTY_LIGHT -> ScreenColors(
            defaultTextColor = Color(0xFF0D1040),
            borderColor = Color(0xFF32366F)
        )
    }
}

data class AppliedTextFormats constructor(
    val index: Int,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val textColor: TextColor = TextColor.COLOR_DEFAULT,
    val fontSize: FontSize = FontSize.MEDIUM,
    val font: Font = Font.MONTSERRAT,
)