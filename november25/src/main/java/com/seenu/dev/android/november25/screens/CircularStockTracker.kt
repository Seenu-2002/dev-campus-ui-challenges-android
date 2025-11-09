package com.seenu.dev.android.november25.screens

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.theme.HostGrotesk
import com.seenu.dev.android.november25.theme.NovemberTheme
import com.seenu.dev.android.november25.theme.discount
import com.seenu.dev.android.november25.theme.textAlt
import com.seenu.dev.android.november25.theme.textDisabled
import com.seenu.dev.android.november25.theme.textPrimary
import com.seenu.dev.android.november25.theme.textSecondary
import kotlinx.coroutines.delay

@Preview
@Composable
private fun CircularStockTrackerPreview() {
    NovemberTheme {
        CircularStockTracker()
    }
}

@Composable
fun CircularStockTracker() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        StockCard()
    }
}

@Preview
@Composable
private fun StockCardPreview() {
    NovemberTheme {
        StockCard()
    }
}

@Composable
fun StockCard(modifier: Modifier = Modifier) {
    var quantityRemaining by remember {
        mutableStateOf(12)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = MaterialTheme.shapes.medium,
                shadow = Shadow(
                    radius = 20.dp,
                    color = Color(0x0F211304),
                    offset = DpOffset(0.dp, 4.dp),
                    spread = 0.dp
                )
            )
            .clip(MaterialTheme.shapes.medium)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (quantityRemaining > 0) {
                        it.background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFD33F3F),
                                    Color(0xFF7C1414),
                                )
                            )
                        )
                    } else {
                        it.background(
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
        ) {
            Image(
                painter = painterResource(R.drawable.nike_shoe),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-16).dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp)

        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1F)) {
                    Text(
                        text = "Nike Air Zoom Pegasus 41",
                        fontFamily = HostGrotesk,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.textPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Legendary running shoes with Air Zoom technology and ReactX cushioning for daily training and marathons.",
                        fontFamily = HostGrotesk,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.textDisabled,
                        fontWeight = FontWeight.Normal
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$100",
                        fontFamily = HostGrotesk,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.discount,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$100",
                        fontFamily = HostGrotesk,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.textDisabled,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            SizeChooser()
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(12.dp))
            RemainingQuantityIndicator(remaining = quantityRemaining, total = 20)
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = {
                    quantityRemaining = (quantityRemaining - 1).coerceAtLeast(0)
                }, enabled = quantityRemaining > 0,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.textPrimary,
                    contentColor = MaterialTheme.colorScheme.textAlt,
                    disabledContainerColor = MaterialTheme.colorScheme.outline,
                    disabledContentColor = MaterialTheme.colorScheme.textAlt
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                val text = if (quantityRemaining > 0) {
                    "Buy"
                } else {
                    "Out of Stock"
                }
                Text(
                    text,
                    fontFamily = HostGrotesk,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun SizeChooser(modifier: Modifier = Modifier) {
    var selectedSize by remember {
        mutableStateOf(42)
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Choose your size",
            fontFamily = HostGrotesk,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.textDisabled,
            fontWeight = FontWeight.Normal,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            for (size in 39..44) {
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.textSecondary,
                            shape = MaterialTheme.shapes.small
                        )
                        .let {
                            if (selectedSize == size) {
                                it.background(
                                    color = MaterialTheme.colorScheme.textSecondary,
                                    shape = MaterialTheme.shapes.small
                                )
                            } else {
                                it
                            }
                        }
                        .clickable {
                            selectedSize = size
                        }
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val textColor = if (selectedSize == size) {
                        MaterialTheme.colorScheme.textAlt
                    } else {
                        MaterialTheme.colorScheme.textSecondary
                    }
                    Text(
                        text = "$size",
                        fontFamily = HostGrotesk,
                        fontSize = 16.sp,
                        color = textColor,
                        fontWeight = FontWeight.Medium,
                    )
                }
                if (size != 44) {
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RemainingQuantityIndicator(
    modifier: Modifier = Modifier,
    remaining: Int = 10,
    total: Int = 100
) {
    var trigger by remember { mutableStateOf(false) }
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }

    val transition = updateTransition(targetState = remaining, label = "ProgressTransition")
    val animatedRemaining by transition.animateFloat(
        label = "AnimatedProgress"
    ) { target ->
        target.toFloat()
    }

    val scale by transition.animateFloat(
        label = "BounceScale"
    ) {
        if (trigger) 1.3F else
            1F
    }

    LaunchedEffect(remaining) {
        if (isFirstLaunch) {
            isFirstLaunch = false
            return@LaunchedEffect
        }

        trigger = true
        delay(200)
        trigger = false
    }

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        val outline = MaterialTheme.colorScheme.outline
        Box(
            modifier = Modifier
                .size(36.dp)
                .drawBehind {
                    val width = 1.dp.toPx()
                    val topLeft = Offset(
                        x = width / 2F,
                        y = width / 2F
                    )
                    val size = Size(
                        width = this.size.width - width,
                        height = this.size.height - width
                    )
                    drawArc(
                        color = outline,
                        style = Stroke(1.dp.toPx()),
                        topLeft = topLeft,
                        size = size,
                        startAngle = 0F,
                        sweepAngle = 360F,
                        useCenter = true
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = {
                    animatedRemaining / total.toFloat()
                },
                trackColor = Color.Transparent,
                color = MaterialTheme.colorScheme.discount,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Square,
                modifier = Modifier
            )

            Text(
                text = "$remaining",
                fontFamily = HostGrotesk,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Remaining at discounted price",
            fontFamily = HostGrotesk,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.textSecondary,
            fontWeight = FontWeight.Normal,
        )
    }
}