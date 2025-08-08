package com.seenu.dev.android.devcampusuichallenges.screens.august

import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seenu.dev.android.devcampusuichallenges.R
import com.seenu.dev.android.devcampusuichallenges.screens.august.theme.AugustTheme
import com.seenu.dev.android.devcampusuichallenges.screens.august.theme.HostGrotesk
import com.seenu.dev.android.devcampusuichallenges.screens.august.theme.surfaceHigher
import com.seenu.dev.android.devcampusuichallenges.screens.august.theme.textDisabled
import com.seenu.dev.android.devcampusuichallenges.screens.august.theme.textPrimary
import com.seenu.dev.android.devcampusuichallenges.screens.august.theme.textSecondary
import com.seenu.dev.android.devcampusuichallenges.screens.august.viewmodels.ThermometerTreckViewModel
import java.text.DecimalFormat

@Composable
fun ThermometerTreckScreen() {

    val viewModel: ThermometerTreckViewModel = viewModel()
    val isTrackingStarted = viewModel.isTrackingStarted.collectAsState().value
    val maxReadingCount = viewModel.maxReadingCount
    val temperatures = viewModel.temperatures.collectAsState().value

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

            if (!isTrackingStarted) {
                ThermometerTreckInfo(
                    modifier = Modifier.padding(
                        horizontal = 24.dp
                    ),
                    onStart = {
                        viewModel.startTracking()
                    }
                )
            } else {
                ThermometerTrackingContent(
                    modifier = Modifier.padding(
                        horizontal = 24.dp
                    ),
                    readings = temperatures,
                    maxReadingCount = maxReadingCount,
                    onReset = viewModel::reset
                )
            }

        }
    }
}

@Preview
@Composable
private fun ThermometerTreckInfoPreview() {
    AugustTheme {
        ThermometerTreckInfo()
    }
}

@Composable
fun ThermometerTreckInfo(modifier: Modifier = Modifier, onStart: () -> Unit = {}) {
    Column(
        modifier = modifier
            .background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceHigher
            )
            .padding(horizontal = 32.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.thermometer_treck_title),
            fontSize = 32.sp,
            fontFamily = HostGrotesk,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Text(
            text = stringResource(R.string.thermometer_trek_description),
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

@Preview
@Composable
private fun ThermometerTrackingContentPreview() {
    AugustTheme {
        val readings = listOf(98.6, 99.5, 100.2, 101.0, 102.5, 103.0)
        ThermometerTrackingContent(readings = readings, maxReadingCount = 20) {}
    }
}

@Composable
fun ThermometerTrackingContent(
    modifier: Modifier = Modifier,
    readings: List<Double>,
    maxReadingCount: Int,
    onReset: () -> Unit
) {

    val noOfColumns = 2
    val maxItemPerColumn = maxReadingCount / noOfColumns

    Column(
        modifier = modifier
            .background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceHigher
            )
            .padding(horizontal = 32.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.thermometer_treck_title),
            fontSize = 32.sp,
            fontFamily = HostGrotesk,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.textPrimary
        )
        ReadingCounter(modifier = Modifier, count = readings.size, max = maxReadingCount)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(noOfColumns) {
                val decimalFormat = remember {
                    DecimalFormat("#.##")
                }
                Column(modifier = Modifier.weight(1F)) {
                    val start = it * maxItemPerColumn
                    val end = start + maxItemPerColumn

                    for (index in start until end) {
                        var iconTint = MaterialTheme.colorScheme.textDisabled
                        var readingText = ""

                        if (index < readings.size) {
                            iconTint = MaterialTheme.colorScheme.primary
                            val temperatureFormatted = decimalFormat.format(readings[index])
                            readingText =
                                stringResource(
                                    R.string.temperature_in_fahrenheit,
                                    temperatureFormatted
                                )
                        }

                        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.ic_thermometer),
                                contentDescription = "Temperature",
                                tint = iconTint,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = readingText,
                                color = MaterialTheme.colorScheme.textPrimary,
                                fontFamily = HostGrotesk,
                                fontWeight = FontWeight.Medium,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        val isTracking = readings.size < maxReadingCount
        val text = if (isTracking) {
            stringResource(R.string.label_tracking)
        } else {
            stringResource(R.string.label_reset)
        }
        Button(
            onClick = onReset,
            enabled = !isTracking,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = MaterialTheme.colorScheme.textDisabled,
                contentColor = MaterialTheme.colorScheme.surfaceHigher,
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = text, fontFamily = HostGrotesk, fontSize = 17.sp)
        }
    }
}

@Preview
@Composable
private fun ReadingCounterPreview() {
    AugustTheme {
        Column() {
            ReadingCounter(count = 5, max = 20)
            ReadingCounter(count = 20, max = 20)
        }
    }
}

@Composable
fun ReadingCounter(modifier: Modifier = Modifier, count: Int, max: Int) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        val tint = if (count == max) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.textDisabled
        }
        Icon(
            painter = painterResource(R.drawable.ic_check_with_circle),
            contentDescription = "",
            tint = tint,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = stringResource(
                R.string.thermometer_treck_reading_counter,
                count,
                max
            ),
            fontFamily = HostGrotesk,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.textSecondary,
        )
    }
}