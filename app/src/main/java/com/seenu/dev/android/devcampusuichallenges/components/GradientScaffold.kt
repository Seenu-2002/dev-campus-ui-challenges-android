package com.seenu.dev.android.devcampusuichallenges.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientScaffold(
    modifier: Modifier = Modifier,
    brush: Brush,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentColor: Color = contentColorFor(MaterialTheme.colorScheme.background),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    Box(modifier.background(brush = brush)) {
        Scaffold(
            modifier = Modifier.matchParentSize(),
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            contentColor = contentColor,
            contentWindowInsets = contentWindowInsets,
            containerColor = Color.Transparent,
            content = content
        )
    }
}