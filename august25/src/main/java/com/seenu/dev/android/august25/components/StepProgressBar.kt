package com.seenu.dev.android.august25.components

import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seenu.dev.android.august25.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
private fun StepProgressBarPreview() {
    var fillPercentage by remember {
        mutableFloatStateOf(1.01F)
    }
    StepProgressBarWithOverflow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(32.dp),
        barHeight = 12.dp,
        steps = 4,
        fillPercentage = fillPercentage
    )

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                delay(2000)  // Simulate a delay before changing the fill percentage
                // Simulate a change in fill percentage
                fillPercentage = (1..150).random() / 100F
            }
        }
    }
}

@Composable
fun StepProgressBarWithOverflow(
    modifier: Modifier = Modifier,
    steps: Int,
    fillPercentage: Float,
    trackColor: Color = Color.LightGray,
    fillColor: Color = Color.Red,
    barHeight: Dp = 24.dp,
    overflowColor: Color = Color(0xFFFFA500),
    overflowGradientColor: Color = Color(0xFFFFE5B4),
    gradientLineWidth: Dp = 1.dp,
    spaceBetweenSteps: Dp = 2.dp,
    percentageTextStyle: TextStyle = TextStyle.Default,
) {
    val isOverflown = fillPercentage > 1F
    val infiniteAnimation = rememberInfiniteTransition()
    val animationPathCount by infiniteAnimation.animateValue(
        initialValue = 1,
        targetValue = 8,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    val textMeasurer = rememberTextMeasurer()

    val text = stringResource(R.string.percentage_100)

    Canvas(modifier = modifier.padding(horizontal = 4.dp)) {
        val (width, _) = this.size
        val height = barHeight.toPx().coerceAtMost(size.height)
        val spaceInPx = spaceBetweenSteps.toPx()
        val paths = mutableListOf<Path>()

        val widthFor100Percent = if (isOverflown) {
            width / fillPercentage.coerceAtMost(2F)
        } else width

        val stepWidth = (widthFor100Percent - (spaceInPx * (steps - 1))) / steps

        for (i in 0 until steps) {
            val left = i * (stepWidth + spaceInPx)
            val right = left + stepWidth
            val path = Path().apply {
                moveTo(left, 0F)
                lineTo(right, 0F)
                lineTo(right, height)
                lineTo(left, height)
                close()
            }
            paths.add(path)
        }

        if (isOverflown) {
            val animPaths = mutableListOf<Path>()
            val gradientLineWidthInPx = gradientLineWidth.toPx()

            val factor = 0.5F
            for (index in 1..animationPathCount) {
                val path1 = Path().apply {
                    val topLeft =
                        Offset(
                            index * -factor * gradientLineWidthInPx,
                            index * -factor * gradientLineWidthInPx
                        )
                    val size = Size(
                        width + (index * (2 * factor) * gradientLineWidthInPx),
                        height + (index * (2 * factor) * gradientLineWidthInPx)
                    )
                    addRoundRect(
                        RoundRect(
                            rect = Rect(topLeft, size),
                            cornerRadius = CornerRadius(size.height / 2F)
                        )
                    )
                }
                animPaths.add(path1)
            }

            for (path in animPaths) {
                drawPath(
                    path,
                    color = overflowGradientColor,
                    style = Stroke(width = gradientLineWidthInPx)
                )
            }

            val path = Path().apply {
                val left = paths.size * (stepWidth + spaceInPx)
                val right = width
                moveTo(left, 0F)
                lineTo(right, 0F)
                lineTo(right, height)
                lineTo(left, height)
                close()
            }
            paths.add(path)

            val textLayoutResult = textMeasurer.measure(text, style = percentageTextStyle)
            val topLeft = Offset(
                ((paths.size - 1) * (stepWidth + spaceInPx)) - (textLayoutResult.size.width / 2F),
                barHeight.toPx() + 8.dp.toPx()
            )
            drawText(textLayoutResult, topLeft = topLeft)
        }

        val clipPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(Offset.Zero, Size(width, height)),
                    cornerRadius = CornerRadius(height / 2F)
                )
            )
        }

        clipPath(path = clipPath, clipOp = ClipOp.Intersect) {
            drawRect(
                color = Color.Unspecified,
                topLeft = Offset.Zero,
                size = size,
            )

            val percentagePerStep = 100F / steps
            val fillCount = ((fillPercentage * 100) / percentagePerStep).toInt()
            val remaining = (fillPercentage * 100F) - (fillCount * percentagePerStep)
            for ((index, path) in paths.withIndex()) {
                if (!isOverflown) {
                    drawPath(path, color = trackColor)
                    when {
                        index < fillCount -> {
                            drawPath(path, color = fillColor)
                        }

                        index == fillCount -> {
                            val rect = Rect(
                                offset = Offset.Zero,
                                size = Size(
                                    width = stepWidth * (remaining / percentagePerStep),
                                    height = height
                                )
                            )

                            drawRect(
                                color = fillColor,
                                topLeft = rect.topLeft + Offset(
                                    x = index * (stepWidth + spaceInPx),
                                    y = 0F
                                ),
                                size = rect.size
                            )
                        }
                    }

                } else {
                    drawPath(path, color = overflowColor)
                }
            }
        }

    }

}