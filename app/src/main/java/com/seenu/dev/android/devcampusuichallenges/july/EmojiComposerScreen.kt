package com.seenu.dev.android.devcampusuichallenges.july

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.devcampusuichallenges.july.theme.JulyTheme
import com.seenu.dev.android.devcampusuichallenges.july.theme.surfaceAlt15
import kotlinx.coroutines.delay

@Composable
fun EmojiComposerScreen() {
}

@Composable
fun EmojiRow(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun EmojiContainerPreview() {
    var isFocused by remember {
        mutableStateOf(false)
    }
    JulyTheme {
        LaunchedEffect(Unit) {
            while (true) {
                isFocused = !isFocused
                delay(1000)
            }
        }
        EmojiContainer(
            modifier = Modifier.size(40.dp),
            emoji = "\uD83D\uDC96",
            isFocused = isFocused
        )
    }
}

@Composable
fun EmojiContainer(
    modifier: Modifier = Modifier,
    emoji: String = "\uD83D\uDC96",
    isFocused: Boolean = false
) {

    var scale by remember {
        mutableFloatStateOf(1F)
    }
    var alpha by remember {
        mutableFloatStateOf(0F)
    }
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        label = "Emoji Scale Animation",
        animationSpec = tween(300)
    )
    val animateAlpha by animateFloatAsState(
        targetValue = alpha,
        label = "Emoji Alpha Animation",
        animationSpec = tween(300)
    )

    LaunchedEffect(isFocused) {
        if (isFocused) {
            scale = 1.2F
            alpha = 1F
        } else {
            scale = 1F
            alpha = 0F
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        val backgroundColor = MaterialTheme.colorScheme.surfaceAlt15
        Text(
            modifier = Modifier.graphicsLayer {
                this.scaleX = animatedScale
                this.scaleY = animatedScale
            }.drawBehind {
                if (isFocused) {
                    drawRoundRect(
                        color = backgroundColor.copy(animateAlpha),
                        size = size,
                        cornerRadius = CornerRadius(
                            x = 12.dp.toPx(),
                            y = 12.dp.toPx()
                        )
                    )
                }
            },
            text = emoji,
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                fontSize = 18.sp
            )
        )
    }
}