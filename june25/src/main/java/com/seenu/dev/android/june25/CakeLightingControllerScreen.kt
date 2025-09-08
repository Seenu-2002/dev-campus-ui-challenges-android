package com.seenu.dev.android.june25

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.seenu.dev.android.june25.theme.Mali

@Preview
@Composable
fun CakeLightingControllerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF1A597B)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var isAllList by remember {
            mutableStateOf(false)
        }

        Text(
            text = "Ready for cake \uD83C\uDF89 ",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFFFFF5EB),
            fontFamily = Mali,
            fontWeight = FontWeight.Bold
        )

        Candles(modifier = Modifier.fillMaxWidth())

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF95D3ED),
            ), onClick = {
                isAllList = !isAllList
            }) {
            Text(
                text = "Light all candles",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF113345),
                fontFamily = Mali,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun Candles(modifier: Modifier = Modifier, isAllLit: Boolean = false) {
    val candles = listOf(
        R.drawable.candle_1,
        R.drawable.candle_2,
        R.drawable.candle_3,
        R.drawable.candle_4,
        R.drawable.candle_5,
        R.drawable.candle_6,
        R.drawable.candle_7,
    )

    var candleStates = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(candles.size) {
                add(false)
            }
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val coroutineScope = rememberCoroutineScope()
        var index = 0
        while (index < candles.size) {
            val candleRes = candles[index]


            if (index % 3 == 0) {
                Box(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    Candle(
                        modifier = Modifier,
                        candleRes = candleRes,
                        isLit = candleStates[index]
                    )
                    index++
                }
            } else {
                Column {
                    repeat(2) {
                        val candleRes = candles[index]
                        Candle(
                            modifier = Modifier,
                            candleRes = candleRes,
                            isLit = candleStates[index]
                        )
                        index++
                    }
                }
            }
        }
    }
}

@Composable
fun Candle(
    modifier: Modifier = Modifier,
    @DrawableRes candleRes: Int,
    isLit: Boolean = false,
    onCandleClick: (() -> Unit)? = null
) {
    val (candleFireStateRes, contentDescription) = if (isLit) {
        R.drawable.candle_fire_state_on to "Candle is lit"
    } else {
        R.drawable.candle_fire_state_off to "Candle is not lit"
    }
    Column(modifier.clickable {
        onCandleClick?.invoke()
    }, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(candleFireStateRes),
            contentDescription = contentDescription
        )
        Image(
            painter = painterResource(candleRes),
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun CandlePreview() {
    Candle(
        candleRes = R.drawable.candle_1,
        isLit = true,
        modifier = Modifier
    )
}

@Preview
@Composable
private fun CandlePreviewLitOff() {
    Candle(
        candleRes = R.drawable.candle_1,
        isLit = false,
        modifier = Modifier
    )
}