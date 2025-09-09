package com.seenu.dev.android.september25

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.september25.theme.Parkinsans
import com.seenu.dev.android.september25.theme.SeptemberTheme
import com.seenu.dev.android.september25.theme.lime
import com.seenu.dev.android.september25.theme.orange
import com.seenu.dev.android.september25.theme.pink
import com.seenu.dev.android.september25.theme.textPrimary
import com.seenu.dev.android.september25.theme.textSecondary
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ExpandableListScreenPreview() {
    Surface {
        ExpandableListScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableListScreen() {

    val context = LocalContext.current

    val pink = MaterialTheme.colorScheme.pink
    val orange = MaterialTheme.colorScheme.orange
    val lime = MaterialTheme.colorScheme.lime

    val stages = remember {
        listOf(
            Stage(
                id = 1,
                title = context.getString(R.string.stage_a),
                shows = listOf(
                    Show(
                        context.getString(R.string.stage_a_performer_1),
                        context.getString(R.string.stage_a_performer_1_time),
                    ),
                    Show(
                        context.getString(R.string.stage_a_performer_2),
                        context.getString(R.string.stage_a_performer_2_time),
                    ),
                ),
                color = lime
            ),
            Stage(
                id = 2,
                title = context.getString(R.string.stage_b),
                shows = listOf(
                    Show(
                        context.getString(R.string.stage_b_performer_1),
                        context.getString(R.string.stage_b_performer_1_time),
                    ),
                    Show(
                        context.getString(R.string.stage_b_performer_2),
                        context.getString(R.string.stage_b_performer_2_time),
                    ),
                    Show(
                        context.getString(R.string.stage_b_performer_3),
                        context.getString(R.string.stage_b_performer_3_time),
                    ),
                ),
                color = orange
            ),
            Stage(
                id = 3,
                title = context.getString(R.string.stage_c),
                shows = listOf(
                    Show(
                        context.getString(R.string.stage_c_performer_1),
                        context.getString(R.string.stage_c_performer_1_time),
                    ),
                    Show(
                        context.getString(R.string.stage_c_performer_2),
                        context.getString(R.string.stage_c_performer_2_time),
                    ),
                ),
                color = pink
            )
        )
    }
    var expandedItemIndex by rememberSaveable {
        mutableIntStateOf(-1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeDrawing
            )
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = stringResource(R.string.festival_lineup),
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Parkinsans,
                lineHeight = (60 * .9F).sp,
                fontSize = 60.sp
            )
            Text(
                text = stringResource(R.string.festival_lineup_subtitle),
                color = MaterialTheme.colorScheme.textSecondary,
                fontWeight = FontWeight.Normal,
                fontFamily = Parkinsans,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        ExpandableList(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F)
                .padding(horizontal = 4.dp),
            expandedItemIndex = expandedItemIndex,
            items = stages,
            onOpen = { index, _ ->
                expandedItemIndex = index
            },
            onClose = { index, _ ->
                if (expandedItemIndex == index) {
                    expandedItemIndex = -1
                }
            })
    }
}

@Composable
fun ExpandableList(
    modifier: Modifier = Modifier,
    items: List<Stage>,
    expandedItemIndex: Int,
    onOpen: (Int, Stage) -> Unit,
    onClose: (Int, Stage) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items.size) { index ->
            val item = items[index]
            ExpandableListItem(stage = item, isExpanded = index == expandedItemIndex, onOpen = {
                onOpen(index, item)
            }, onClose = {
                onClose(index, item)
            })

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
private fun ExpandableListItemPreview() {
    SeptemberTheme {
        val item = Stage(
            121,
            "Stage 2",
            listOf(
                Show("The Weekend", "13:00"),
                Show("Dua Lipa", "14:15"),
                Show("Drake", "15:30"),
            ),
            color = MaterialTheme.colorScheme.pink
        )
        var isExpanded by remember { mutableStateOf(false) }


        ExpandableListItem(
            modifier = Modifier,
            stage = item,
            isExpanded = isExpanded,
            onOpen = {
                isExpanded = true
            },
            onClose = {
                isExpanded = false
            })
    }
}

@Composable
fun ExpandableListItem(
    modifier: Modifier = Modifier,
    stage: Stage,
    isExpanded: Boolean,
    onOpen: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = stage.color,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (isExpanded) {
                        onClose()
                    } else {
                        onOpen()
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .animateContentSize(),
                text = stage.title,
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Parkinsans,
                fontSize = 38.sp
            )
            IconButton(onClick = {
                if (isExpanded) {
                    onClose()
                } else {
                    onOpen()
                }
            }) {

                val iconRes = if (isExpanded) {
                    R.drawable.ic_minus
                } else {
                    R.drawable.ic_add
                }

                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.textPrimary
                )
            }
        }
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                for ((index, show) in stage.shows.withIndex()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = show.artist,
                            color = MaterialTheme.colorScheme.textPrimary,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Parkinsans,
                            fontSize = 24.sp
                        )
                        Text(
                            text = show.time,
                            color = MaterialTheme.colorScheme.textPrimary,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Parkinsans,
                            fontSize = 24.sp
                        )
                    }

                    if (index != stage.shows.lastIndex) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.textPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Serializable
data class Stage constructor(
    val id: Long,
    val title: String,
    val shows: List<Show>,
    val color: Color = Color.Unspecified
)

@Serializable
data class Show constructor(
    val artist: String,
    val time: String
)