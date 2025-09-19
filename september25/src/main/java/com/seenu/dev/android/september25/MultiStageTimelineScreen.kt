package com.seenu.dev.android.september25

import android.R.attr.rowHeight
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollable2DState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable2D
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.seenu.dev.android.september25.theme.Parkinsans
import com.seenu.dev.android.september25.theme.SeptemberTheme
import com.seenu.dev.android.september25.theme.lime
import com.seenu.dev.android.september25.theme.orange
import com.seenu.dev.android.september25.theme.overlay
import com.seenu.dev.android.september25.theme.purple
import com.seenu.dev.android.september25.theme.textPrimary
import com.seenu.dev.android.september25.theme.textSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.to

@Preview
@Composable
private fun MultiStageTimelineScreenPreview() {
    SeptemberTheme {
        Surface {
            MultiStageTimelineScreen()
        }
    }
}

@Composable
fun MultiStageTimelineScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(top = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .zIndex(1F)
                .background(color = MaterialTheme.colorScheme.surface),
            text = stringResource(R.string.festival_schedule),
            color = MaterialTheme.colorScheme.textSecondary,
            fontWeight = FontWeight.Medium,
            fontFamily = Parkinsans,
            fontSize = 20.sp
        )

        MultiStageTimeline(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .clipToBounds(),
            headers = listOf("Main", "Rock", "Electro"),
            stages = listOf(
                StageWithTime(
                    type = StageType.ELECTRO,
                    artist = "DJ A",
                    from = 12F,
                    to = 13F,
                    timeLabel = "12:00-13:00"
                ),
                StageWithTime(
                    type = StageType.MAIN,
                    artist = "Band X",
                    from = 13F,
                    to = 14.5F,
                    timeLabel = "13:00-14:30"
                ),
                StageWithTime(
                    type = StageType.ROCK,
                    artist = "RockZ",
                    from = 14F,
                    to = 15F,
                    timeLabel = "14:00-15:00"
                ),
                StageWithTime(
                    type = StageType.ELECTRO,
                    artist = "Ambient Line",
                    from = 15F,
                    to = 16.5F,
                    timeLabel = "15:00-16:30"
                ),
                StageWithTime(
                    type = StageType.MAIN,
                    artist = "Florence + The Machine",
                    from = 16.5F,
                    to = 18F,
                    timeLabel = "16:30-18:00"
                ),
                StageWithTime(
                    type = StageType.ROCK,
                    artist = "The National",
                    from = 17F,
                    to = 18F,
                    timeLabel = "17:00-18:00"
                ),
                StageWithTime(
                    type = StageType.ELECTRO,
                    artist = "Jamie xx",
                    from = 18F,
                    to = 19F,
                    timeLabel = "18:00-19:00"
                ),
                StageWithTime(
                    type = StageType.MAIN,
                    artist = "Tame Impala",
                    from = 19F,
                    to = 20.5F,
                    timeLabel = "19:00-20:30"
                ),
                StageWithTime(
                    type = StageType.ROCK,
                    artist = "Arctic Monkeys",
                    from = 20F,
                    to = 21.5F,
                    timeLabel = "20:00-21:30"
                ),
                StageWithTime(
                    type = StageType.MAIN,
                    artist = "Radiohead",
                    from = 21.5F,
                    to = 23F,
                    timeLabel = "21:30-23:00"
                )
            )

        )
    }
}

data class StageWithTime constructor(
    val type: StageType,
    val artist: String,
    val from: Float,
    val to: Float,
    val timeLabel: String
)

enum class StageType constructor(val index: Int) {
    MAIN(0), ROCK(1), ELECTRO(2)
}

@Composable
fun StageType.getColor(): Color {
    return when (this) {
        StageType.MAIN -> MaterialTheme.colorScheme.orange
        StageType.ROCK -> MaterialTheme.colorScheme.purple
        StageType.ELECTRO -> MaterialTheme.colorScheme.lime
    }
}

@Composable
fun MultiStageTimeline(
    modifier: Modifier = Modifier,
    headers: List<String>,
    stages: List<StageWithTime>
) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var scale by remember {
        mutableFloatStateOf(1F)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    var maxOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val intervalVerticalScroll = rememberScrollState()
    val scroll2DState = rememberScrollable2DState { delta ->

        var consumedX = 0f
        var consumedY = 0f

        val newX = offset.x + delta.x
        if (newX in -maxOffset.x..maxOffset.x) {
            offset = offset.copy(x = newX)
            consumedX = delta.x
        } else {
            offset = offset.copy(x = newX.coerceIn(-maxOffset.x, maxOffset.x))
            consumedX = delta.x - (newX - offset.x) // only part was consumed
        }

        if (scale > 1f) {
            val newY = offset.y + delta.y
            if (newY in -maxOffset.y..maxOffset.y) {
                offset = offset.copy(y = newY)
                consumedY = delta.y
            } else {
                val clampedY = newY.coerceIn(-maxOffset.y, maxOffset.y)
                val remaining = newY - clampedY
                offset = offset.copy(y = clampedY)

                consumedY =
                    delta.y - remaining + intervalVerticalScroll.dispatchRawDelta(-remaining)
            }
        } else {
            consumedY = intervalVerticalScroll.dispatchRawDelta(-delta.y)
        }

        Offset(consumedX, consumedY)
    }
    val transformable = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1F, 2.5F)

        val extraWidth = (scale - 1) * size.width
        val extraHeight = (scale - 1) * size.height

        val maxX = extraWidth / 2F
        val maxY = extraHeight / 2F
        maxOffset = Offset(x = maxX, y = maxY)

        offset = Offset(
            x = (offset.x + panChange.x).coerceIn(-maxX, maxX),
            y = (offset.y + panChange.y).coerceIn(-maxY, maxY)
        )
    }

    Box(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state = transformable)
                .scrollable2D(state = scroll2DState)
                .onGloballyPositioned {
                    size = it.size
                }) {
            val (header,
                column,
                divider,
                content,
                hint) = createRefs()
            var headersRowWidth by remember {
                mutableFloatStateOf(0F)
            }
            var intervalColumnHeight by remember {
                mutableFloatStateOf(0F)
            }
            val defaultVisibleColumnCount = 7

            Row(
                modifier = Modifier
                    .constrainAs(header) {
                        top.linkTo(parent.top)
                        start.linkTo(column.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned {
                        headersRowWidth = it.size.width.toFloat()
                    }) {
                val width = with(LocalDensity.current) { (headersRowWidth / headers.size).toDp() }
                for (header in headers) {
                    Text(
                        text = header,
                        fontFamily = Parkinsans,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.textPrimary,
                        modifier = Modifier
                            .width(width)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.constrainAs(divider) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }.zIndex(1F), color = MaterialTheme.colorScheme.textPrimary, thickness = 2.dp
            )
            Box(
                modifier = Modifier
                    .constrainAs(column) {
                        top.linkTo(divider.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        height = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned {
                        intervalColumnHeight = it.size.height.toFloat()
                    },
                contentAlignment = Alignment.TopStart
            ) {

                val gap =
                    with(LocalDensity.current) { (intervalColumnHeight / defaultVisibleColumnCount).toDp() }
                IntervalColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(enabled = false, state = intervalVerticalScroll),
                    gap = gap,
                    count = 12,
                    topPadding = with(LocalDensity.current) { 15.sp.toPx() }
                ) {
                    Text(
                        text = "${12 + it}:00",
                        fontFamily = Parkinsans,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.textSecondary,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .wrapContentSize()
                    )
                }
            }
            val dividerColor = MaterialTheme.colorScheme.outline
            Box(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(column.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) {

                val totalIntervals = 12
                val rowHeightPx = intervalColumnHeight / defaultVisibleColumnCount
                val contentHeight = rowHeightPx * totalIntervals

                var height by remember {
                    mutableFloatStateOf(0F)
                }
                OffsetGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = with(LocalDensity.current) {
                            contentHeight.toDp()
                        })
                        .verticalScroll(enabled = false, state = intervalVerticalScroll)
                        .drawWithContent {

                            for (vLineIndex in 0..(headers.size + 1)) {
                                val x = vLineIndex * (headersRowWidth / headers.size)
                                drawLine(
                                    color = dividerColor,
                                    start = Offset(x, 0F),
                                    end = Offset(x, this.size.height),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }

                            var y = 15.sp.toPx()
                            val gapBetweenLines =
                                (intervalColumnHeight / defaultVisibleColumnCount) / 2F
                            while (y <= height) {
                                Log.e("MultiStageTimeline", "y: $y, height: $height")
                                drawLine(
                                    color = dividerColor,
                                    start = Offset(0F, y),
                                    end = Offset(this.size.width, y),
                                    strokeWidth = 1.dp.toPx()
                                )
                                y += gapBetweenLines
                            }
                            drawContent()
                        }.onGloballyPositioned {
                            height = it.size.height.toFloat()
                        },
                    rows = headers.size,
                    columns = 23 - 12 + 1,
                    cellSize = IntSize(
                        width = (headersRowWidth / headers.size).toInt(),
                        height = (intervalColumnHeight / defaultVisibleColumnCount).toInt()
                    )
                ) {
                    for (stage in stages) {
                        item(
                            rowStart = stage.from - 12F,
                            rowEnd = stage.to - 12F,
                            column = stage.type.index
                        ) {
                            TimelineItem(
                                stageWithTime = stage,
                            )
                        }
                    }
                }
            }

            var showHint by remember {
                mutableStateOf(true)
            }
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                scope.launch {
                    delay((2000L..3000L).random())
                    showHint = false
                }
            }

            AnimatedVisibility(visible = showHint, modifier = Modifier.constrainAs(hint) {
                top.linkTo(divider.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }) {
                PinchToZoomHint(modifier = Modifier.fillMaxSize())
            }
        }


        val zoomPercentage by remember {
            derivedStateOf {
                ((scale * 10).toInt() % 100) * 10
            }
        }

        AnimatedVisibility(
            visible = scale > 1.0F,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            ZoomIndicator(percentage = zoomPercentage)
        }
    }
}

@Preview
@Composable
private fun TimelineItemPreview() {
    SeptemberTheme {
        TimelineItem(
            stageWithTime = StageWithTime(
                type = StageType.MAIN,
                artist = "Imagine Dragons",
                from = 13.5F,
                to = 15F,
                "13:00-15:00"
            )
        )
    }
}

@Composable
fun TimelineItem(modifier: Modifier = Modifier, stageWithTime: StageWithTime) {
    Column(
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 4.dp)
            .background(
                color = stageWithTime.type.getColor(),
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = stageWithTime.timeLabel,
            fontFamily = Parkinsans,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Text(
            text = stageWithTime.artist,
            fontFamily = Parkinsans,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.textPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun IntervalColumn(
    modifier: Modifier = Modifier,
    count: Int,
    topPadding: Float,
    gap: Dp,
    text: @Composable (Int) -> Unit
) {
    Layout(modifier = modifier, content = {
        for (index in 0..<count) {
            text(index)
        }
    }) { measurables, constraints ->
        var maxWidth = 0

        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            if (placeable.width > maxWidth) {
                maxWidth = placeable.width
            }

            placeable
        }

        val gapInPx = gap.toPx()

        layout(width = maxWidth, height = (gapInPx * count).roundToInt()) {
            for ((index, placeable) in placeables.withIndex()) {
                val y = (topPadding + (index * gapInPx)) - (placeable.height / 2F)
                placeable.place(0, y.roundToInt())
            }
        }
    }
}

@Composable
fun OffsetGrid(
    modifier: Modifier,
    rows: Int,
    columns: Int,
    cellSize: IntSize,
    content: OffsetGridScope.() -> Unit
) {
    val latestContent = rememberUpdatedState(content)
    val scope = remember {
        OffsetGridScopeImpl().apply {
            latestContent.value.invoke(this)
        }
    }

    Layout(modifier = modifier, content = {
        for (item in scope.items) {
            item.content()
        }
    }) { measurables, constraints ->
        val items = scope.items
        val placeables = measurables.mapIndexed { index, it ->
            val item = items[index]
            val percentage = item.rowEnd - item.rowStart
            it.measure(
                constraints.copy(
                    minWidth = cellSize.width,
                    maxWidth = cellSize.width,
                    minHeight = (cellSize.height * percentage).roundToInt(),
                    maxHeight = (cellSize.height * percentage).roundToInt()
                )
            )
        }

        layout(width = rows * cellSize.width, height = columns * cellSize.height) {
            placeables.forEachIndexed { index, placeable ->
                val item = items[index]
                if (item.rowEnd > columns) {
                    throw IllegalStateException("Row End ${item.rowEnd} is out of bounds. There are only $rows rows.")
                }

                if (item.column > rows) {
                    throw IllegalStateException("Column index ${item.column} is out of bounds. There are only $columns columns.")
                }

                val x = (item.column * cellSize.width)
                val y = ((item.rowStart * cellSize.height) + 15.sp.toPx()).roundToInt()
                placeable.place(x, y)
            }
        }
    }
}


@Composable
fun PinchToZoomHint(modifier: Modifier = Modifier) {
    Surface(color = Color.Unspecified) {
        Box(
            modifier = modifier.background(color = MaterialTheme.colorScheme.overlay.copy(alpha = .75F)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.pinch_to_zoom),
                fontFamily = Parkinsans,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Preview
@Composable
private fun ZoomIndicatorPreview() {
    SeptemberTheme {
        ZoomIndicator(percentage = 120)
    }
}

@Composable
fun ZoomIndicator(modifier: Modifier = Modifier, percentage: Int) {
    Text(
        text = stringResource(R.string.zoom_percentage, percentage),
        fontFamily = Parkinsans,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.overlay.copy(alpha = .75F),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp, vertical = 2.dp)
    )
}

interface OffsetGridScope {
    fun item(rowStart: Float, rowEnd: Float, column: Int, content: @Composable () -> Unit)
}

class OffsetGridScopeImpl : OffsetGridScope {

    val items = mutableListOf<Item>()

    override fun item(
        rowStart: Float,
        rowEnd: Float,
        column: Int,
        content: @Composable () -> Unit
    ) {
        items.add(Item(rowStart = rowStart, rowEnd = rowEnd, column = column, content = content))
    }

    class Item constructor(
        val rowStart: Float,
        val rowEnd: Float,
        val column: Int,
        val content: @Composable () -> Unit
    )
}