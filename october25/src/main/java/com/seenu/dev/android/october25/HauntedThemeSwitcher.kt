package com.seenu.dev.android.october25

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.seenu.dev.android.october25.theme.OctoberTheme
import com.seenu.dev.android.october25.theme.day
import com.seenu.dev.android.october25.theme.night
import kotlinx.coroutines.launch

private const val ANIMATION_DURATION = 1000

@Preview(
    device = Devices.PIXEL_4_XL
)
@Composable
private fun HauntedThemeSwitcherPreview() {
    OctoberTheme {
        HauntedThemeSwitcher()
    }
}

@Composable
fun HauntedThemeSwitcher(modifier: Modifier = Modifier) {
    val imageScrollState = rememberScrollState()
    var time by remember {
        mutableStateOf(Time.DAY)
    }

    val transition = updateTransition(targetState = time, label = "ThemeTransition")
    val dayAlpha by transition.animateFloat(
        transitionSpec = { tween(ANIMATION_DURATION) }, label = "dayAlpha"
    ) { if (it == Time.DAY) 1F else 0F }

    val nightAlpha by transition.animateFloat(
        transitionSpec = { tween(ANIMATION_DURATION) }, label = "nightAlpha"
    ) { if (it == Time.DAY) 0F else 1F }

    val dayColor = MaterialTheme.colorScheme.day
    val nightColor = MaterialTheme.colorScheme.night
    val backgroundColor by transition.animateColor(
        transitionSpec = { tween(ANIMATION_DURATION) }, label = "backgroundColor"
    ) { if (it == Time.DAY) dayColor else nightColor }

    LaunchedEffect(Unit) {
        imageScrollState.scrollTo(
            if (time == Time.DAY) {
                imageScrollState.maxValue
            } else {
                0
            }
        )
    }

    LaunchedEffect(time) {
        val target = if (time == Time.DAY) imageScrollState.maxValue else 0
        if (imageScrollState.value != target) {
            imageScrollState.animateScrollTo(target, tween(ANIMATION_DURATION))
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.8F)
                .align(Alignment.BottomStart)
                .horizontalScroll(state = imageScrollState, enabled = false)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_graveyard),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
            )
        }
        val tintLayerColor = Color.Black.copy(alpha = nightAlpha / 2F)
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = tintLayerColor)
        )

        Day(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4F)
                .align(Alignment.TopCenter)
                .padding(top = 80.dp), isVisible = time == Time.DAY
        )

        Night(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6F)
                .align(Alignment.TopCenter),
            isVisible = time == Time.NIGHT
        )

        PumpkinSlider(
            time = time, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            time = if (time == Time.DAY) {
                Time.NIGHT
            } else {
                Time.DAY
            }
        }
    }
}

enum class Time {
    DAY, NIGHT
}

@Preview
@Composable
private fun PumpkinSliderPreview() {
    OctoberTheme {
        var time by remember {
            mutableStateOf(Time.DAY)
        }
        PumpkinSlider(
            time = time, onTimeClicked = {
                time = if (time == Time.DAY) {
                    Time.NIGHT
                } else {
                    Time.DAY
                }
            }, modifier = Modifier
        )
    }
}

@Composable
fun PumpkinSlider(modifier: Modifier = Modifier, time: Time, onTimeClicked: () -> Unit) {
    val width = 120.dp
    val targetOffset = if (time == Time.DAY) 44.dp else 0.dp
    val offsetX by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(ANIMATION_DURATION),
        label = "pumpkinOffset"
    )
    Box(
        modifier = modifier
            .width(width)
            .height(60.dp)
            .background(color = Color.White, shape = CircleShape)
            .clickable(onClick = onTimeClicked)
            .clip(shape = CircleShape)
            .padding(vertical = 3.dp, horizontal = 12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_pumpkin),
            contentDescription = null,
            modifier = Modifier
                .offset(x = offsetX.value.dp, y = -(4).dp)
                .size(53.57.dp, 56.dp)
        )
    }
}

@Preview
@Composable
private fun DayPreview() {
    OctoberTheme {
        var isVisible by remember {
            mutableStateOf(true)
        }
        Day(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6F)
                .clickable {
                    isVisible = !isVisible
                }, isVisible = isVisible
        )
    }
}

@Composable
fun Day(modifier: Modifier = Modifier, isVisible: Boolean = true) {
    Box(
        modifier = modifier
    ) {
        val fadeAnim = tween<Float>(ANIMATION_DURATION)
        val slideAnim = tween<IntOffset>(ANIMATION_DURATION)

        val rotation = remember {
            Animatable(0F)
        }

        LaunchedEffect(isVisible) {
            val value = if (isVisible) {
                0F
            } else {
                -45F
            }
            rotation.animateTo(value, fadeAnim)
        }

        AnimatedVisibility(
            isVisible, enter = fadeIn(fadeAnim) + slideInHorizontally(slideAnim) {
                -it
            }, exit = fadeOut(fadeAnim) + slideOutHorizontally(tween(ANIMATION_DURATION)) {
                -it
            }, modifier = Modifier
                .offset(x = (-120).dp)
                .align(Alignment.BottomStart)
        ) {
            Image(
                painter = painterResource(
                    R.drawable.ic_cloud_1,
                ),
                contentDescription = null,
            )
        }
        AnimatedVisibility(
            isVisible, enter = fadeIn(fadeAnim) + slideInHorizontally(slideAnim) {
                -it
            }, exit = fadeOut(fadeAnim) + slideOutHorizontally(slideAnim) {
                -it
            }, modifier = Modifier
                .offset(x = -(48).dp)
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(
                    R.drawable.ic_cloud_2,
                ), contentDescription = null
            )
        }

        AnimatedVisibility(
            isVisible, enter = fadeIn(fadeAnim) + slideInHorizontally(slideAnim) {
                it
            }, exit = fadeOut(fadeAnim) + slideOutHorizontally(slideAnim) {
                it
            }, modifier = Modifier
                .offset(x = 120.dp)
                .align(Alignment.CenterEnd)
        ) {
            Image(
                painter = painterResource(
                    R.drawable.ic_cloud_3,
                ), contentDescription = null, modifier = Modifier
            )
        }
        AnimatedVisibility(
            isVisible, enter = fadeIn(fadeAnim) + slideIn(slideAnim) { it ->
                IntOffset(
                    it.width * 2, it.height
                )
            }, exit = fadeOut(fadeAnim) + slideOut(slideAnim) { it ->
                IntOffset(
                    it.width * 2, it.height
                )
            }, modifier = Modifier
                .align(Alignment.Center)
                .offset(x = -(16).dp)
        ) {
            Image(
                painter = painterResource(
                    R.drawable.ic_sun,
                ), contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    rotationZ = rotation.value
                }
            )
        }
    }
}

@Preview
@Composable
private fun NightPreview() {
    OctoberTheme {
        Night(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.8F), isVisible = true
        )
    }
}

@Composable
fun Night(modifier: Modifier = Modifier, isVisible: Boolean = true) {
    val fadeAnim = tween<Float>(ANIMATION_DURATION)
    val slideAnim = tween<IntOffset>(ANIMATION_DURATION)
    val shrinkAnim = tween<IntSize>(ANIMATION_DURATION)

    val rotation = remember {
        Animatable(0F)
    }

    LaunchedEffect(isVisible) {
        val value = if (isVisible) {
            0F
        } else {
            -45F
        }
        rotation.animateTo(value, fadeAnim)
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(fadeAnim),
            exit = fadeOut(fadeAnim),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_stars),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(fadeAnim) + slideIn(slideAnim) { it ->
                IntOffset(
                    -it.width * 2, it.height
                )
            },
            exit = fadeOut(fadeAnim) + slideOut(slideAnim) { it ->
                IntOffset(
                    -it.width * 2, it.height
                )
            },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-16).dp, y = (-40).dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_moon), contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = rotation.value
                    }
            )
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(fadeAnim) + scaleIn(fadeAnim, transformOrigin = TransformOrigin(1F, 1F)),
            exit = fadeOut(fadeAnim) + scaleOut(
                fadeAnim,
                transformOrigin = TransformOrigin(1F, 1F)
            ) + shrinkOut(shrinkAnim),
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_ghost),
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}