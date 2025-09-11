package com.seenu.dev.android.september25

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.september25.theme.Parkinsans
import com.seenu.dev.android.september25.theme.SeptemberTheme
import com.seenu.dev.android.september25.theme.lime
import com.seenu.dev.android.september25.theme.orange
import com.seenu.dev.android.september25.theme.pink
import com.seenu.dev.android.september25.theme.textPrimary
import com.seenu.dev.android.september25.theme.textSecondary
import kotlin.math.roundToInt

@Preview
@Composable
private fun MapChipFilterScreenPreview() {
    SeptemberTheme {
        Surface {
            MapChipFilterScreen()
        }
    }
}

@Composable
fun MapChipFilterScreen() {

    val selectedFilters = remember {
        mutableStateListOf<FestivalMapMarker>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.festival_map),
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Parkinsans,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                for ((index, marker) in FestivalMapMarker.entries.withIndex()) {
                    val data = marker.mapAsFilterChipData()
                    FilterChip(
                        modifier = Modifier,
                        isSelected = selectedFilters.contains(marker),
                        state = data,
                        onClick = {
                            if (selectedFilters.contains(marker)) {
                                selectedFilters.remove(marker)
                            } else {
                                selectedFilters.add(marker)
                            }
                        }
                    )

                    if (index != FestivalMapMarker.entries.lastIndex) {
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.textPrimary
        )
        FestivalMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F), markers = selectedFilters.flatMap { it.getMarkers() })
    }
}

@Preview
@Composable
private fun FestivalMapPreview() {
    SeptemberTheme {
        Surface {
            FestivalMap(
                modifier = Modifier.fillMaxSize(),
                markers = FestivalMapMarker.entries.map { it.getMarkers() }.flatten()
            )
        }
    }
}

data class MarkerWithState constructor(
    @DrawableRes
    val iconRes: Int,
    val x: Float,
    val y: Float
)

@Composable
fun FestivalMap(modifier: Modifier = Modifier, markers: List<MarkerWithState>) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    Box(modifier = modifier.onGloballyPositioned {
        size = it.size
    }) {
        Image(
            painterResource(R.drawable.map),
            contentDescription = "Festival map",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )

        for (marker in markers) {
            Image(
                painter = painterResource(marker.iconRes),
                contentDescription = "",
                modifier = Modifier.offset {
                    IntOffset(
                        x = (size.width * marker.x).roundToInt(),
                        y = (size.height * marker.y).roundToInt()
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun FilterChipPreview() {
    SeptemberTheme {
        FilterChip(
            isSelected = false,
            state = FilterChipState(
                iconRes = R.drawable.ic_stage,
                titleRes = R.string.stages,
                color = MaterialTheme.colorScheme.pink
            )
        )
    }
}

data class FilterChipState constructor(
    @DrawableRes
    val iconRes: Int,
    @StringRes
    val titleRes: Int,
    val color: Color
)

@Composable
fun FilterChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    state: FilterChipState,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                shape = CircleShape,
                color = if (isSelected) state.color else Color.Unspecified
            )
            .clip(
                shape = CircleShape
            )
            .let {
                if (!isSelected) {
                    it.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.textSecondary,
                        shape = CircleShape
                    )
                } else {
                    it
                }
            }
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
            .padding(start = 12.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val contentColor = if (isSelected) {
            MaterialTheme.colorScheme.textPrimary
        } else {
            MaterialTheme.colorScheme.textSecondary
        }
        Icon(
            painter = painterResource(state.iconRes),
            contentDescription = "",
            tint = contentColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(state.titleRes),
            color = contentColor,
            modifier = Modifier,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Parkinsans
        )
    }
}

enum class FestivalMapMarker {
    STAGES, FOOD, WC;
}

fun FestivalMapMarker.getMarkers(): List<MarkerWithState> {
    return when (this) {
        FestivalMapMarker.STAGES -> listOf(
            MarkerWithState(
                R.drawable.pin_stage_a,
                .5F,
                0.04F
            ),
            MarkerWithState(
                R.drawable.pin_stage_b,
                .23F,
                .37F
            ),
            MarkerWithState(
                R.drawable.pin_stage_c,
                .42F,
                .77F
            ),
        )

        FestivalMapMarker.FOOD -> listOf(
            MarkerWithState(
                R.drawable.pin_food,
                .7F,
                .2F
            ),
            MarkerWithState(
                R.drawable.pin_food,
                .26F,
                .54F
            ),
            MarkerWithState(
                R.drawable.pin_food,
                .125F,
                .87F
            ),
        )

        FestivalMapMarker.WC -> listOf(
            MarkerWithState(
                R.drawable.pin_wc,
                .05F,
                .1F
            ),
            MarkerWithState(
                R.drawable.pin_wc,
                .78F,
                .56F
            )
        )
    }
}

@Composable
fun FestivalMapMarker.mapAsFilterChipData(): FilterChipState {
    val title: Int
    val icon: Int
    val color: Color
    when (this) {
        FestivalMapMarker.STAGES -> {
            title = R.string.stages
            icon = R.drawable.ic_stage
            color = MaterialTheme.colorScheme.lime
        }

        FestivalMapMarker.FOOD -> {
            title = R.string.food
            icon = R.drawable.ic_food
            color = MaterialTheme.colorScheme.pink
        }

        FestivalMapMarker.WC -> {
            title = R.string.wc
            icon = R.drawable.ic_wc
            color = MaterialTheme.colorScheme.orange
        }
    }

    return FilterChipState(
        iconRes = icon,
        titleRes = title,
        color = color
    )
}