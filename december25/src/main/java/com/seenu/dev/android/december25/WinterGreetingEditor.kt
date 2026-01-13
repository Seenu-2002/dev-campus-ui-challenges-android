package com.seenu.dev.android.december25

import android.R.attr.centerX
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seenu.dev.android.december25.components.EditorActionBar
import com.seenu.dev.android.december25.components.RichTextEditor
import com.seenu.dev.android.december25.theme.DecemberTheme
import com.seenu.dev.android.december25.theme.Montserrat
import com.seenu.dev.android.december25.theme.PTSans
import com.seenu.dev.android.december25.theme.evergreenWish
import com.seenu.dev.android.december25.theme.frostyLight
import com.seenu.dev.android.december25.theme.goldenEveGradientEnd
import com.seenu.dev.android.december25.theme.goldenEveGradientMiddle
import com.seenu.dev.android.december25.theme.goldenEveGradientStart
import com.seenu.dev.android.december25.theme.snowyWhiteGradientEnd
import com.seenu.dev.android.december25.theme.snowyWhiteGradientMiddle
import com.seenu.dev.android.december25.theme.snowyWhiteGradientStart
import com.seenu.dev.android.december25.viewmodels.WinterGreetingEditorIntent
import com.seenu.dev.android.december25.viewmodels.WinterGreetingEditorViewModel
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinterGreetingEditor() {
    val viewModel: WinterGreetingEditorViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Winter Greeting Editor",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        fontFamily = Montserrat,
                        color = Color(0xFF0D1040)
                    )
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = Color.Red)
            ) {
                // Content goes here
            }
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SnowyWhiteBackground()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                RichTextEditor(
                    state = uiState.state,
                    onStateChange = { newState ->
                        viewModel.updateEditorState(newState)
                    },
                    placeholder = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontStyle = FontStyle.Italic,
                                    )
                                ) {
                                    append("Type your greeting here...")
                                }
                            },
                            fontFamily = PTSans,
                            fontSize = 34.sp,
                            color = Color.White,
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = PTSans,
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .border(1.dp, Color.LightGray)
                        .padding(
                            16.dp
                        )
                )
            }

            EditorActionBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                onBoldSelected = {
                    viewModel.onIntent(WinterGreetingEditorIntent.Bold)
                },
                onItalicSelected = {
                    viewModel.onIntent(WinterGreetingEditorIntent.Italic)
                },
                onUnderlineSelected = {
                    viewModel.onIntent(WinterGreetingEditorIntent.Underline)
                }
            )
        }
    }
}

@Preview
@Composable
private fun SnowyWhiteBackgroundPreview() {
    DecemberTheme {
        SnowyWhiteBackground()
    }
}

@Composable
fun SnowyWhiteBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.snowyWhiteGradientStart,
                        MaterialTheme.colorScheme.snowyWhiteGradientMiddle,
                        MaterialTheme.colorScheme.snowyWhiteGradientEnd,
                    )
                )
            )
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            painter = painterResource(id = R.drawable.img_snowy_night_decor_top),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            painter = painterResource(id = R.drawable.img_snowy_night_decor_bottom),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
private fun FrostyLightBackgroundPreview() {
    DecemberTheme {
        FrostyLightBackground()
    }
}

@Composable
fun FrostyLightBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.frostyLight)
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            painter = painterResource(id = R.drawable.img_frosty_light_1),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.img_sparkle_background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            painter = painterResource(id = R.drawable.img_frosty_light_2),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
private fun EvergreenWishBackgroundPreview() {
    DecemberTheme {
        EvergreenWishBackground()
    }
}

@Composable
fun EvergreenWishBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.evergreenWish)
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth(.48F)
                .align(Alignment.TopStart),
            painter = painterResource(id = R.drawable.img_evergreen_wish_top_left),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = modifier
                .fillMaxWidth(.48F)
                .align(Alignment.TopEnd),
            painter = painterResource(id = R.drawable.img_evergreen_wish_top_right),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            painter = painterResource(id = R.drawable.img_evergreen_wish_bottom),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
private fun GoldenEveBackgroundPreview() {
    DecemberTheme {
        GoldenEveBackground()
    }
}

@Composable
fun GoldenEveBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.goldenEveGradientStart,
                        MaterialTheme.colorScheme.goldenEveGradientMiddle,
                        MaterialTheme.colorScheme.goldenEveGradientEnd,
                    )
                )
            )
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.img_golden_eve_decor),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}