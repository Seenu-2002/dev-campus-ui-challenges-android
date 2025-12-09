package com.seenu.dev.android.december25

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.december25.theme.DecemberTheme
import com.seenu.dev.android.december25.theme.Montserrat
import com.seenu.dev.android.december25.theme.keyHoverGradientEnd
import com.seenu.dev.android.december25.theme.keyHoverGradientStart
import com.seenu.dev.android.december25.theme.title
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(
    name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = false
)

@Composable
private fun SantaClapPianoPreview() {
    DecemberTheme {
        SantaClapPiano()
    }
}

@Composable
fun SantaClapPiano() {

    val context = LocalContext.current
    val activity = LocalActivity.current
    val configuration = LocalConfiguration.current
    if (activity != null) {
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        DisposableEffect(Unit) {
            onDispose {
                if (configuration.orientation != Configuration.ORIENTATION_PORTRAIT) {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
                }
            }
        }
    }

    val player = remember {
        SantaPianoKeyboardPlayer(context.applicationContext)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Background
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.left_decor),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight()
                )

                Image(
                    painter = painterResource(R.drawable.right_decor),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight()
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Santa's Piano",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 34.sp,
                    color = MaterialTheme.colorScheme.title
                )
                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.Black, shape = RoundedCornerShape(21.48.dp))
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val cornerRadius = 16.11.dp
                        for (index in 0..<7) {
                            val shape = when (index) {
                                0 -> RoundedCornerShape(
                                    topStart = cornerRadius,
                                    bottomStart = cornerRadius,
                                    bottomEnd = cornerRadius
                                )

                                6 -> RoundedCornerShape(
                                    topEnd = cornerRadius,
                                    bottomStart = cornerRadius,
                                    bottomEnd = cornerRadius,
                                )

                                else -> RoundedCornerShape(
                                    bottomStart = cornerRadius,
                                    bottomEnd = cornerRadius
                                )
                            }

                            PianoKey(
                                onPressed = {
                                    player.playNote(index)
                                },
                                shape = shape
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val shape = RoundedCornerShape(
                            bottomStart = 13.43.dp,
                            bottomEnd = 13.43.dp,
                        )
                        repeat(6) {
                            Box(
                                modifier = Modifier
                                    .width(32.dp)
                                    .fillMaxHeight(.55F)
                                    .dropShadow(
                                        shape = shape,
                                        shadow = androidx.compose.ui.graphics.shadow.Shadow(
                                            radius = 16.11.dp,
                                            offset = DpOffset(0.dp, 16.11.dp),
                                            color = Color(0x3B000000),
                                        )
                                    )
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFF121212),
                                                Color(0xFF232323),
                                            )
                                        ), shape = shape
                                    )
                                    .border(
                                        width = 2.69.dp,
                                        shape = shape, color = Color(0xFF171717)
                                    )

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PianoKey(
    shape: Shape,
    onPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var isPressed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            coroutineScope.launch {
                delay((300L .. 500L).random())
                isPressed = false
            }
        }
    }
    Box(
        modifier = modifier
            .width(74.dp)
            .fillMaxHeight(.6F)
            .let {
                if (isPressed) {
                    it.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.keyHoverGradientStart,
                                MaterialTheme.colorScheme.keyHoverGradientEnd,
                            )
                        ),
                        shape = shape
                    )
                } else {
                    it.background(color = Color.White, shape = shape)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        onPressed()
                        awaitRelease()
                    }
                )
            }
    )
}

