package com.seenu.dev.android.devcampusuichallenges.july

import android.R.attr.strokeColor
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.seenu.dev.android.devcampusuichallenges.R
import com.seenu.dev.android.devcampusuichallenges.components.GradientScaffold
import com.seenu.dev.android.devcampusuichallenges.july.theme.JulyTheme
import com.seenu.dev.android.devcampusuichallenges.july.theme.Urbanist
import com.seenu.dev.android.devcampusuichallenges.july.theme.backgroundGradientEnd
import com.seenu.dev.android.devcampusuichallenges.july.theme.onSurfaceAlt
import com.seenu.dev.android.devcampusuichallenges.july.theme.surface30
import com.seenu.dev.android.devcampusuichallenges.july.theme.surface50
import kotlinx.serialization.Serializable

@Composable
fun BottomNavigationWithUnreadBadgesScreen() {
    val backStack = rememberNavBackStack<Screen>(Screen.Settings)
    val screens = remember {
        mutableStateListOf(
            Screen.Chats(hasNotification = false),
            Screen.Calls(hasNotification = false),
            Screen.Settings
        )
    }

    JulyTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NavDisplay(
                modifier = Modifier.matchParentSize(),
                backStack = backStack,
                entryProvider = entryProvider {

                    entry<Screen.Chats>(
                        metadata = NavDisplay.transitionSpec {
                            val previousDestination = backStack.getOrNull(backStack.lastIndex - 1)
                            when (previousDestination) {
                                is Screen.Calls, is Screen.Settings -> {
                                    slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                            slideOutHorizontally(targetOffsetX = { it })
                                }

                                else -> {
                                    // Default or first navigation: no animation
                                    EnterTransition.None togetherWith ExitTransition.None
                                }
                            }
                        } + NavDisplay.popTransitionSpec {
                            // When popping back to A: slide in from left
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { it })
                        }
                    ) { entry ->
                        ScreenHolder(title = stringResource(R.string.chats))
                    }

                    entry<Screen.Calls>(metadata = NavDisplay.transitionSpec {
                        val previousDestination = backStack.getOrNull(backStack.lastIndex - 1)
                        when (previousDestination) {
                            is Screen.Chats -> {
                                slideInHorizontally(initialOffsetX = { it }) togetherWith
                                        slideOutHorizontally(targetOffsetX = { -it })
                            }

                            is Screen.Settings -> {
                                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                        slideOutHorizontally(targetOffsetX = { it })
                            }

                            else -> {
                                slideInHorizontally(initialOffsetX = { it }) togetherWith
                                        slideOutHorizontally(targetOffsetX = { -it })
                            }
                        }
                    } + NavDisplay.popTransitionSpec {
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { it })
                    }) { entry ->
                        ScreenHolder(title = stringResource(R.string.calls))
                    }

                    entry<Screen.Settings>(
                        metadata = NavDisplay.transitionSpec {
                            slideInHorizontally(initialOffsetX = { it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { -it })
                        } + NavDisplay.popTransitionSpec {
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { it })
                        }
                    ) {
                        ScreenHolder(title = stringResource(R.string.settings)) {
                            Column(
                                modifier = Modifier
                                    .width(IntrinsicSize.Max)
                                    .align(alignment = Alignment.Center)
                            ) {
                                CustomButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    strokeColor = MaterialTheme.colorScheme.primary,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    textColor = MaterialTheme.colorScheme.onSurfaceAlt,
                                    icon = painterResource(R.drawable.icon_call),
                                    text = stringResource(R.string.miss_a_call),
                                    onClick = {
                                        screens[1] = Screen.Calls(hasNotification = true)
                                    }
                                )
                                CustomButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    strokeColor = MaterialTheme.colorScheme.primary,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    textColor = MaterialTheme.colorScheme.onSurfaceAlt,
                                    icon = painterResource(R.drawable.icon_message),
                                    text = stringResource(R.string.send_message),
                                    onClick = {
                                        screens[0] = Screen.Chats(hasNotification = true)
                                    }
                                )

                                CustomButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    strokeColor = MaterialTheme.colorScheme.primary,
                                    backgroundColor = MaterialTheme.colorScheme.background,
                                    textColor = MaterialTheme.colorScheme.primary,
                                    icon = painterResource(R.drawable.icon_read_tinted),
                                    text = stringResource(R.string.mark_as_read),
                                    onClick = {
                                        screens[0] = Screen.Chats(hasNotification = false)
                                    }
                                )
                            }
                        }
                    }
                })

            FloatingNavBar(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                screens = screens,
                selectedScreen = backStack.last() as Screen
            ) { screen ->
                val newScreen = when (screen) {
                    is Screen.Chats -> Screen.Chats(hasNotification = false)
                    is Screen.Calls -> Screen.Calls(hasNotification = false)
                    is Screen.Settings -> Screen.Settings
                }
                val index = screens.indexOf(screen)
                screens[index] = newScreen
                backStack.add(newScreen)
            }
        }
    }
}

@Preview
@Composable
private fun FloatingNavBarPreview() {
    val screens = listOf(
        Screen.Chats(hasNotification = false),
        Screen.Calls(hasNotification = true),
        Screen.Settings
    )
    var selected by remember {
        mutableStateOf(screens[2])
    }
    JulyTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            FloatingNavBar(
                screens = screens,
                selectedScreen = selected,
            ) {
                selected = it
            }
        }
    }

}

@Composable
fun FloatingNavBar(
    modifier: Modifier = Modifier,
    screens: List<Screen>,
    selectedScreen: Screen,
    onSelected: (Screen) -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = modifier
            .background(
                shape = shape,
                color = MaterialTheme.colorScheme.surface30
            )
            .border(
                1.dp, color = MaterialTheme.colorScheme.surface50, shape = shape
            )
            .padding(end = 4.dp)
    ) {
        for (screen in screens) {
            FloatingNavbarItem(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 4.dp),
                icon = when (screen) {
                    is Screen.Chats -> painterResource(R.drawable.icon_message)
                    is Screen.Calls -> painterResource(R.drawable.icon_call)
                    is Screen.Settings -> painterResource(R.drawable.icon_settings)
                },
                showBadge = when (screen) {
                    is Screen.Chats -> screen.hasNotification
                    is Screen.Calls -> screen.hasNotification
                    else -> false
                },
                isSelected = selectedScreen == screen,
                contentDescription = when (screen) {
                    is Screen.Chats -> stringResource(R.string.chats)
                    is Screen.Calls -> stringResource(R.string.calls)
                    is Screen.Settings -> stringResource(R.string.settings)
                },
                onClick = { onSelected(screen) }
            )
        }
    }
}

@Preview
@Composable
private fun FloatingNavbarItemPreview() {
    JulyTheme {
        FloatingNavbarItem(
            icon = painterResource(R.drawable.icon_message),
            showBadge = true,
            isSelected = true,
            contentDescription = stringResource(R.string.chats),
        )
    }
}

@Composable
fun FloatingNavbarItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    showBadge: Boolean,
    isSelected: Boolean,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val (modifier, iconTint) = if (isSelected) {
            Modifier
                .size(56.dp)
                .padding(4.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(8.dp)
                ) to MaterialTheme.colorScheme.onSurfaceAlt
        } else {
            Modifier
                .size(56.dp)
                .padding(4.dp) to MaterialTheme.colorScheme.onSurfaceVariant
        }
        Box(modifier = modifier.clickable {
            onClick()
        }, contentAlignment = Alignment.Center) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = iconTint,
            )
        }

        if (showBadge) {
            val shape = RoundedCornerShape(percent = 50)
            Spacer(
                modifier = Modifier
                    .padding(16.dp)
                    .size(4.dp)
                    .background(shape = shape, color = MaterialTheme.colorScheme.error)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHolder(title: String, content: @Composable BoxScope.() -> Unit = {}) {
    GradientScaffold(
        modifier = Modifier
            .fillMaxSize(),
        brush = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.backgroundGradientEnd
            )
        ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text(
                        text = title,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 25.sp
                    )
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Preview
@Composable
private fun CustomButtonPreview() {
    JulyTheme {
        CustomButton(
            strokeColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.background,
            textColor = MaterialTheme.colorScheme.primary,
            icon = painterResource(R.drawable.icon_read_tinted),
            text = stringResource(R.string.mark_as_read),
        )
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    strokeColor: Color,
    backgroundColor: Color,
    icon: Painter,
    textColor: Color,
    text: String,
    onClick: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .background(color = backgroundColor)
            .border(
                1.dp,
                color = strokeColor
            )
            .padding(horizontal = 32.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(start = 8.dp),
                painter = icon,
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                fontFamily = Urbanist,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }

}

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data class Chats(val hasNotification: Boolean) : Screen

    @Serializable
    data class Calls(val hasNotification: Boolean) : Screen

    @Serializable
    data object Settings : Screen
}