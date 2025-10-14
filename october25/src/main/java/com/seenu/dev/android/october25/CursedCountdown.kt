package com.seenu.dev.android.october25

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.october25.theme.OctoberTheme
import com.seenu.dev.android.october25.theme.RoadRage
import com.seenu.dev.android.october25.theme.outlineInactive
import com.seenu.dev.android.october25.theme.skeletonToastBg
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import kotlin.time.ExperimentalTime

@Preview
@Composable
fun CursedCountdownPreview() {
    OctoberTheme {
        CursedCountdown()
    }
}

@Composable
fun CursedCountdown() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.outlineInactive)
            .consumeWindowInsets(WindowInsets.systemBars)
    ) {
        Image(
            painter = painterResource(R.drawable.spider_web),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(.5F)
                .align(
                    Alignment.TopStart
                )
        )

        Image(
            painter = painterResource(R.drawable.graveyard),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.BottomCenter
                )
        )

        Timer(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun Timer(modifier: Modifier = Modifier) {
    var countdown by remember {
        mutableStateOf(getRemainingDurationAsString())
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                delay(1000)
                Log.d("CursedCountdown", "Timer: ${getRemainingDurationAsString()}")
                countdown = getRemainingDurationAsString()
            }
        }
    }

    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.skeletonToastBg,
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = 60.dp, horizontal = 45.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (char in countdown) {
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    (slideInVertically { it } + fadeIn() togetherWith
                            slideOutVertically { -it } + fadeOut()).using(SizeTransform(clip = false))
                }
            ) { char ->
                Text(
                    "$char",
                    fontFamily = RoadRage,
                    fontSize = 64.sp,
                    color = MaterialTheme.colorScheme.background,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
fun getRemainingDurationAsString(): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, 2025)
    calendar.set(Calendar.MONTH, Calendar.OCTOBER)
    calendar.set(Calendar.DAY_OF_MONTH, 31)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    return getRemainingDurationUntilAsString(calendar.timeInMillis)
}

@OptIn(ExperimentalTime::class)
fun getRemainingDurationUntilAsString(target: Long): String {
    val totalMillis = target - System.currentTimeMillis()
    if (totalMillis <= 0) return "00D 00:00:00"

    val totalSeconds = totalMillis / 1000
    val days = totalSeconds / (24 * 3600)
    val hours = (totalSeconds % (24 * 3600)) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return String.format(Locale.ENGLISH, "%02dD %02d:%02d:%02d", days, hours, minutes, seconds)
}