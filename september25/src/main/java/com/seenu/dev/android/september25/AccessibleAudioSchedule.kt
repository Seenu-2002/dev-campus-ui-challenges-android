package com.seenu.dev.android.september25

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.september25.theme.Parkinsans
import com.seenu.dev.android.september25.theme.SeptemberTheme
import com.seenu.dev.android.september25.theme.blue
import com.seenu.dev.android.september25.theme.lime
import com.seenu.dev.android.september25.theme.orange
import com.seenu.dev.android.september25.theme.pink
import com.seenu.dev.android.september25.theme.purple
import com.seenu.dev.android.september25.theme.surfaceHigher
import com.seenu.dev.android.september25.theme.textPrimary
import com.seenu.dev.android.september25.theme.textSecondary
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Preview
@Composable
private fun AccessibleAudioSchedulePreview() {
    SeptemberTheme {
        Surface {
            AccessibleAudioSchedule()
        }
    }
}

@Composable
fun AccessibleAudioSchedule() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeDrawing
            )
            .padding(top = 16.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.schedule),
            color = MaterialTheme.colorScheme.textPrimary,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Parkinsans,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        val schedules = schedules
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(horizontal = 4.dp)
                .padding(top = 4.dp)
        ) {
            items(schedules.size) { index ->
                val schedule = schedules[index]
                AudioScheduleRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    data = schedule
                )
            }
        }
    }
}

data class AudioScheduleItem constructor(
    val id: String,
    val artist: String,
    val type: MusicType,
    val time: String,
    val stage: String,
    val desc: String
)

enum class MusicType {
    INDIE, ELECTRONIC, HEADLINER, EXPERIMENTAL, ALT_ROCK;
}

@Composable
fun MusicType.getResources(): Pair<Int, Color> {
    return when (this) {
        MusicType.INDIE -> Pair(R.string.type_indie, MaterialTheme.colorScheme.orange)
        MusicType.ELECTRONIC -> Pair(R.string.type_electronic, MaterialTheme.colorScheme.lime)
        MusicType.HEADLINER -> Pair(R.string.type_headliner, MaterialTheme.colorScheme.purple)
        MusicType.EXPERIMENTAL -> Pair(R.string.type_experimental, MaterialTheme.colorScheme.pink)
        MusicType.ALT_ROCK -> Pair(R.string.type_alt_rock, MaterialTheme.colorScheme.blue)
    }
}

@Preview
@Composable
private fun AudioScheduleRowPreview() {
    SeptemberTheme {
        val data = AudioScheduleItem(
            id = "1212",
            artist = "Bon Iver",
            type = MusicType.INDIE,
            stage = "Main Stage",
            time = "15:30",
            desc = "Atmospheric folk-electronic set to start the day"
        )
        AudioScheduleRow(modifier = Modifier.fillMaxWidth(), data = data)
    }
}

@Composable
fun AudioScheduleRow(modifier: Modifier = Modifier, data: AudioScheduleItem) {
    val contentDescription = stringResource(
        R.string.accessibility_card_format,
        stringResource(data.type.getResources().first),
        data.artist,
        data.time,
        data.stage,
        data.desc
    )
    Column(
        modifier = modifier
            .clearAndSetSemantics {
                this.contentDescription = contentDescription
            }
            .background(
                color = MaterialTheme.colorScheme.surfaceHigher,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        val type = data.type.getResources()
        Text(
            text = stringResource(type.first),
            modifier = Modifier
                .background(shape = CircleShape, color = type.second)
                .padding(horizontal = 8.dp, vertical = 2.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.textPrimary,
            fontFamily = Parkinsans
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(
                    iterations = 2,
                    initialDelayMillis = 1000,
                    repeatDelayMillis = 1000,
                    velocity = 34.dp,
                    spacing = MarqueeSpacing(40.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.artist,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.textPrimary,
                fontFamily = Parkinsans
            )
            Spacer(
                modifier = Modifier
                    .width(24.dp)
            )
            Spacer(
                modifier = Modifier
                    .weight(1F)
            )
            Text(
                text = stringResource(R.string.time_and_stage_format, data.time, data.stage),
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.textPrimary,
                fontFamily = Parkinsans
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = data.desc,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.textSecondary,
            fontFamily = Parkinsans,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
private val schedules
    @Composable
    get() = listOf(
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_1),
            type = MusicType.INDIE,
            time = stringResource(R.string.artist_1_time),
            stage = stringResource(R.string.artist_1_stage),
            desc = stringResource(R.string.artist_1_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_2),
            type = MusicType.ELECTRONIC,
            time = stringResource(R.string.artist_2_time),
            stage = stringResource(R.string.artist_2_stage),
            desc = stringResource(R.string.artist_2_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_3),
            type = MusicType.HEADLINER,
            time = stringResource(R.string.artist_3_time),
            stage = stringResource(R.string.artist_3_stage),
            desc = stringResource(R.string.artist_3_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_4),
            type = MusicType.INDIE,
            time = stringResource(R.string.artist_4_time),
            stage = stringResource(R.string.artist_4_stage),
            desc = stringResource(R.string.artist_4_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_5),
            type = MusicType.EXPERIMENTAL,
            time = stringResource(R.string.artist_5_time),
            stage = stringResource(R.string.artist_5_stage),
            desc = stringResource(R.string.artist_5_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_6),
            type = MusicType.INDIE,
            time = stringResource(R.string.artist_6_time),
            stage = stringResource(R.string.artist_6_stage),
            desc = stringResource(R.string.artist_6_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_7),
            type = MusicType.ELECTRONIC,
            time = stringResource(R.string.artist_7_time),
            stage = stringResource(R.string.artist_7_stage),
            desc = stringResource(R.string.artist_7_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_8),
            type = MusicType.HEADLINER,
            time = stringResource(R.string.artist_8_time),
            stage = stringResource(R.string.artist_8_stage),
            desc = stringResource(R.string.artist_8_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_9),
            type = MusicType.ALT_ROCK,
            time = stringResource(R.string.artist_9_time),
            stage = stringResource(R.string.artist_9_stage),
            desc = stringResource(R.string.artist_9_desc)
        ),
        AudioScheduleItem(
            id = Uuid.random().toString(),
            artist = stringResource(R.string.artist_10),
            type = MusicType.HEADLINER,
            time = stringResource(R.string.artist_10_time),
            stage = stringResource(R.string.artist_10_stage),
            desc = stringResource(R.string.artist_10_desc)
        )
    )
