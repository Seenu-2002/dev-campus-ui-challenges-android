package com.seenu.dev.android.october25.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.seenu.dev.android.october25.R

val Background = Color(0xFF1A0D39)
val ToastBg = Color(0xFFFF934A)
val TextActive = Color(0xFF39271A)
val TextPrimary = Color(0xFFF5F3EF)
val TextSecondary = Color(0xFFD2C6ED)
val TextDisabled = Color(0xFF9276CF)
val FrameActive = Color(0xFFD2B5A0)
val FrameInactive = Color(0xFF7A60B5)
val OutlineActive = Color(0xFF462F78)
val OutlineInactive = Color(0xFF2C1B53)
val OutlineError = Color(0xFFF7406E)
val Slot = Color(0xFF533E82)
val SlotActive = Color(0x33462f78)
val Day = Color(0xFFFEEEE2)
val Night = Color(0xFF4D2EAA)
val BoneColor = Color(0xFFF5F3EF)
val SurfaceHigher = Color(0x801A0D39)

val colorScheme = darkColorScheme(
    background = Background,
)

val ColorScheme.outlineInactive: Color
    get() = OutlineInactive

val ColorScheme.toastBg: Color
    get() = ToastBg

val ColorScheme.textActive: Color
    get() = TextActive

val ColorScheme.textPrimary: Color
    get() = TextPrimary

val ColorScheme.textSecondary: Color
    get() = TextSecondary

val ColorScheme.textDisabled: Color
    get() = TextDisabled

val ColorScheme.frameActive: Color
    get() = FrameActive

val ColorScheme.frameInactive: Color
    get() = FrameInactive

val ColorScheme.outlineActive: Color
    get() = OutlineActive

val ColorScheme.slot: Color
    get() = Slot

val ColorScheme.day: Color
    get() = Day

val ColorScheme.night: Color
    get() = Night

val ColorScheme.boneColor: Color
    get() = BoneColor

val ColorScheme.surfaceHigher: Color
    get() = SurfaceHigher

val ColorScheme.outlineError: Color
    get() = OutlineError

val ColorScheme.slotActive: Color
    get() = SlotActive

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

val Cinzel = FontFamily(
    Font(
        R.font.cinzel_regular,
        weight = FontWeight.Normal
    ),
    Font(
        R.font.cinzel_bold,
        weight = FontWeight.Bold
    ),
    Font(
        R.font.cinzel_semibold,
        weight = FontWeight.SemiBold
    ),
)

val Montserrat = FontFamily(
    Font(
        R.font.montserrat_regular,
        weight = FontWeight.Normal
    ),
    Font(
        R.font.montserrat_bold,
        weight = FontWeight.Bold
    ),
    Font(
        R.font.montserrat_semibold,
        weight = FontWeight.SemiBold
    ),
)

@Composable
fun OctoberTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colorScheme, content = content)
}