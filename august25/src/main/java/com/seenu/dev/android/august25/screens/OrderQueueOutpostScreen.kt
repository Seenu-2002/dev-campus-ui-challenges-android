package com.seenu.dev.android.august25.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seenu.dev.android.august25.R
import com.seenu.dev.android.august25.components.StepProgressBarWithOverflow
import com.seenu.dev.android.august25.theme.AugustTheme
import com.seenu.dev.android.august25.theme.HostGrotesk
import com.seenu.dev.android.august25.theme.overload
import com.seenu.dev.android.august25.theme.surfaceHigher
import com.seenu.dev.android.august25.theme.surfaceHighest
import com.seenu.dev.android.august25.theme.textPrimary
import com.seenu.dev.android.august25.theme.textSecondary
import com.seenu.dev.android.august25.theme.warning
import com.seenu.dev.android.august25.viewmodels.OrderCollectionStatus
import com.seenu.dev.android.august25.viewmodels.OrderQueueOutpostViewModel

@Composable
fun OrderQueueOutpostScreen() {
    val viewModel: OrderQueueOutpostViewModel = viewModel()

    val isStartedProcessing = viewModel.isStarted.collectAsStateWithLifecycle().value
    val collectionStatus = viewModel.orderCollectionStatus.collectAsStateWithLifecycle().value
    val pendingOrderCount = viewModel.pendingOrderCount.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            if (!isStartedProcessing) {
                OrderQueueOutpostInfoCard(
                    modifier = Modifier
                        .padding(20.dp),
                    onStart = {
                        viewModel.startProcessingOrders()
                    }
                )
            } else {
                OrderQueueOutpostQueueCard(
                    modifier = Modifier
                        .padding(20.dp),
                    queueCount = pendingOrderCount,
                    maxCountCanHandle = 25,
                    isPaused = collectionStatus != OrderCollectionStatus.COLLECTING,
                    onPause = {
                        viewModel.pauseCollecting()
                    },
                    onResume = {
                        viewModel.resumeCollecting()
                    }
                )
            }
        }
    }

}

@Preview
@Composable
private fun OrderQueueOutpostQueueCardPreview() {
    AugustTheme {
        var isPaused by remember {
            mutableStateOf(false)
        }
        OrderQueueOutpostQueueCard(
            isPaused = isPaused,
            queueCount = 5,
            maxCountCanHandle = 25,
            onResume = { isPaused = false },
            onPause = { isPaused = true }
        )
    }
}

@Composable
fun OrderQueueOutpostQueueCard(
    modifier: Modifier = Modifier,
    isPaused: Boolean,
    queueCount: Int,
    maxCountCanHandle: Int,
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
) {

    val percentage = queueCount / maxCountCanHandle.toFloat()
    Column(
        modifier = modifier
            .background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceHigher
            )
            .padding(horizontal = 32.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.order_queue_outpost_title),
            fontSize = 32.sp,
            fontFamily = HostGrotesk,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(
                    R.string.queue_with_item_count,
                    queueCount,
                    maxCountCanHandle
                ),
                fontFamily = HostGrotesk,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.textPrimary
            )
            Text(
                text = "${(percentage * 100).toInt()}%",
                fontFamily = HostGrotesk,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.textSecondary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        val fillColor = when (percentage) {
            in 0F.. .33F -> MaterialTheme.colorScheme.primary
            in .34F.. .66F -> MaterialTheme.colorScheme.warning
            else -> MaterialTheme.colorScheme.overload
        }
        StepProgressBarWithOverflow(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            steps = 3,
            barHeight = 12.dp,
            fillPercentage = percentage,
            fillColor = fillColor,
            overflowColor = MaterialTheme.colorScheme.overload
        )

        Spacer(modifier = Modifier.height(24.dp))

        var text: String
        var drawablePainter: Painter
        var buttonContainerColor: Color
        var buttonContentColor: Color

        if (isPaused) {
            text = stringResource(R.string.label_start)
            drawablePainter = painterResource(R.drawable.ic_play)
            buttonContainerColor = MaterialTheme.colorScheme.primary
            buttonContentColor = MaterialTheme.colorScheme.surfaceHigher
        } else {
            text = stringResource(R.string.label_pause)
            drawablePainter = painterResource(R.drawable.ic_pause_with_circle)
            buttonContainerColor = MaterialTheme.colorScheme.surfaceHighest
            buttonContentColor = MaterialTheme.colorScheme.textPrimary
        }
        Button(
            onClick = if (isPaused) onResume else onPause,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor,
                contentColor = buttonContentColor
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = drawablePainter,
                    contentDescription = "",
                    tint = buttonContentColor,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = text, fontFamily = HostGrotesk, fontSize = 17.sp)
            }
        }
    }

}

@Preview
@Composable
private fun OrderQueueOutpostInfoCardPreview() {
    AugustTheme {
        OrderQueueOutpostInfoCard(onStart = {

        })
    }
}

@Composable
fun OrderQueueOutpostInfoCard(modifier: Modifier = Modifier, onStart: () -> Unit) {
    Column(
        modifier = modifier
            .background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceHigher
            )
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.order_queue_outpost_title),
            fontSize = 32.sp,
            fontFamily = HostGrotesk,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Text(
            text = stringResource(R.string.order_queue_outpost_description),
            fontSize = 17.sp,
            fontFamily = HostGrotesk,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.textSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        val shape = MaterialTheme.shapes.medium
        val radius = shape.topStart.toPx(Size(0F, 0F), LocalDensity.current)
        val outlineColor = MaterialTheme.colorScheme.outline
        Button(
            onClick = onStart,
            shape = shape,
            modifier = Modifier.drawWithContent {
                drawContent()

                val brush = Brush.verticalGradient(
                    colors = listOf(
                        outlineColor.copy(alpha = .25F),
                        outlineColor.copy(alpha = 0F)
                    )
                )
                drawRoundRect(
                    brush = brush,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        cap = Stroke.DefaultCap,
                    ),
                    cornerRadius = CornerRadius(radius),
                    topLeft = Offset(0F, 4.dp.toPx()),
                    size = this.size.copy(
                        width = this.size.width,
                        height = this.size.height - 8.dp.toPx()
                    ),
                )
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "Start processing orders",
                    tint = MaterialTheme.colorScheme.surfaceHigher,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = stringResource(R.string.label_start),
                    fontSize = 17.sp,
                    fontFamily = HostGrotesk,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.surfaceHigher
                )
            }
        }
    }
}

@Preview
@Composable
private fun OrderQueueOutpostContentPreview() {
    AugustTheme {
        OrderQueueOutpostContent(
            modifier = Modifier.fillMaxWidth(),
            isCollecting = true,
            pendingOrderCount = 18,
            maxCountCanHandle = 25,
            onPause = {},
            onResume = {}
        )
    }
}

@Composable
fun OrderQueueOutpostContent(
    modifier: Modifier = Modifier,
    isCollecting: Boolean,
    pendingOrderCount: Int,
    maxCountCanHandle: Int,
    onPause: () -> Unit,
    onResume: () -> Unit
) {
    val percentage = pendingOrderCount / maxCountCanHandle.toFloat()
    Column(
        modifier = modifier
            .background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceHigher
            )
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.order_queue_outpost_title),
            fontSize = 32.sp,
            fontFamily = HostGrotesk,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(
                    R.string.queue_with_item_count,
                    pendingOrderCount,
                    maxCountCanHandle
                ),
                fontFamily = HostGrotesk,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.textPrimary
            )
            Text(
                text = "${percentage * 100}%",
                fontFamily = HostGrotesk,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.textSecondary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        val fillColor = when (percentage) {
            in 0F.. .33F -> MaterialTheme.colorScheme.primary
            in .34F.. .66F -> MaterialTheme.colorScheme.warning
            else -> MaterialTheme.colorScheme.overload
        }
        StepProgressBarWithOverflow(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            barHeight = 12.dp,
            steps = 3,
            fillPercentage = percentage,
            fillColor = fillColor,
            overflowColor = MaterialTheme.colorScheme.overload
        )

        Button(onClick = {
            if (isCollecting) onPause() else onResume()
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val (text, iconRes) = if (isCollecting) {
                    R.string.label_pause to R.drawable.ic_pause_with_circle
                } else {
                    R.string.label_resume to R.drawable.ic_play
                }

                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = stringResource(text),
                    tint = MaterialTheme.colorScheme.surfaceHigher,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = stringResource(text), fontFamily = HostGrotesk, fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.surfaceHigher,
                )
            }
        }
    }
}