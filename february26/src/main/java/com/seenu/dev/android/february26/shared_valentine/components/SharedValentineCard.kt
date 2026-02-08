package com.seenu.dev.android.february26.shared_valentine.components

import android.R.attr.onClick
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seenu.dev.android.february26.shared_valentine.surfaceActive
import kotlinx.coroutines.launch


@Composable
fun SharedValentineCard(
    imageRes: Int,
    isSelected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    defaultScale: Float = 1F,
    showBorderInInactiveState: Boolean = false,
    modifier: Modifier = Modifier
) {
    var scale by remember {
        mutableStateOf(Animatable(if (isSelected) 1.3F else defaultScale))
    }
    val activeColor = MaterialTheme.colorScheme.surfaceActive
    val inactiveColor = MaterialTheme.colorScheme.surface
    var backgroundColor by remember {
        mutableStateOf(
            Animatable(if (isSelected) activeColor else inactiveColor)
        )
    }
    val borderActiveColor = MaterialTheme.colorScheme.outline
    val borderInactiveColor =
        if (showBorderInInactiveState) MaterialTheme.colorScheme.surface else Color.Transparent
    var borderColor by remember {
        mutableStateOf(
            Animatable(if (isSelected) borderActiveColor else borderInactiveColor)
        )
    }

    LaunchedEffect(isSelected) {
        val scaleAnimSpec = spring<Float>(
            stiffness = 500F,
            dampingRatio = 0.5F
        )
        val colorAnimSpec = spring<Color>(
            stiffness = 500F,
            dampingRatio = 0.5F
        )
        if (isSelected) {
            launch {
                scale.animateTo(
                    targetValue = 1.3F,
                    animationSpec = scaleAnimSpec
                )
            }
            launch {
                backgroundColor.animateTo(
                    targetValue = activeColor,
                    animationSpec = colorAnimSpec
                )
            }
            launch {
                borderColor.animateTo(
                    targetValue = borderActiveColor
                )
            }
        } else {
            launch {
                scale.animateTo(
                    targetValue = defaultScale,
                    animationSpec = scaleAnimSpec
                )
            }
            launch {
                backgroundColor.animateTo(
                    targetValue = inactiveColor,
                    animationSpec = colorAnimSpec
                )
            }
            launch {
                borderColor.animateTo(
                    targetValue = borderInactiveColor
                )
            }
        }
    }

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor.value,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(12.dp)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                interactionSource = null,
                indication = null
            ), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.background(
                color = if (isSelected) MaterialTheme.colorScheme.surfaceActive else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        if (isSelected) {
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                    }
                    .padding(8.dp)
            )
        }
    }
}