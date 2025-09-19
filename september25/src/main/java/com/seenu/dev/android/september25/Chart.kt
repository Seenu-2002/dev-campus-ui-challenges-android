package com.seenu.dev.android.september25

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

data class DataPoint(val x: Float, val y: Float)

@Preview
@Composable
private fun SplineChartScreenPreview() {
    SplineChartScreen()
}

@Composable
fun SplineChartScreen() {
    val sampleData = remember {
        listOf(
            DataPoint(0f, 20f),
            DataPoint(1f, 45f),
            DataPoint(2f, 28f),
            DataPoint(3f, 80f),
            DataPoint(4f, 35f),
            DataPoint(5f, 65f),
            DataPoint(6f, 42f),
            DataPoint(7f, 78f),
            DataPoint(8f, 55f),
            DataPoint(9f, 90f),
            DataPoint(10f, 30f),
            DataPoint(11f, 72f),
            DataPoint(12f, 48f)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Interactive Spline Chart",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Pinch to zoom • Drag to pan",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp
        ) {
            SplineChart(
                data = sampleData,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Chart Features:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "• Smooth spline interpolation\n• Pinch gesture for zooming\n• Drag gesture for panning\n• Grid lines and axis labels\n• Responsive touch interactions",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun SplineChart(
    data: List<DataPoint>,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val transformableState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.5f, 5f)
        offsetX += offsetChange.x
        offsetY += offsetChange.y
    }

    Canvas(
        modifier = modifier
            .clipToBounds()
            .transformable(state = transformableState)
    ) {
        val padding = 60.dp.toPx()
        val chartWidth = size.width - 2 * padding
        val chartHeight = size.height - 2 * padding

        if (data.isEmpty()) return@Canvas

        val minX = data.minOf { it.x }
        val maxX = data.maxOf { it.x }
        val minY = data.minOf { it.y }
        val maxY = data.maxOf { it.y }

        val xRange = maxX - minX
        val yRange = maxY - minY

        // Apply transformations
        val scaledWidth = chartWidth * scale
        val scaledHeight = chartHeight * scale
        val translationX = offsetX + (size.width - scaledWidth) / 2
        val translationY = offsetY + (size.height - scaledHeight) / 2

        // Draw grid
        drawGrid(
            chartWidth = scaledWidth,
            chartHeight = scaledHeight,
            translationX = translationX,
            translationY = translationY,
            padding = padding,
            minX = minX,
            maxX = maxX,
            minY = minY,
            maxY = maxY
        )

        // Convert data points to screen coordinates
        val points = data.map { point ->
            val x = translationX + padding + (point.x - minX) / xRange * scaledWidth
            val y = translationY + padding + scaledHeight - (point.y - minY) / yRange * scaledHeight
            Offset(x, y)
        }

        // Draw spline curve
        drawSpline(points)

        // Draw data points
        points.forEach { point ->
            drawCircle(
                color = Color(0xFF2196F3),
                radius = 6.dp.toPx(),
                center = point
            )
            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx(),
                center = point
            )
        }

        // Draw axes
        drawAxes(
            chartWidth = scaledWidth,
            chartHeight = scaledHeight,
            translationX = translationX,
            translationY = translationY,
            padding = padding
        )
    }
}

fun DrawScope.drawGrid(
    chartWidth: Float,
    chartHeight: Float,
    translationX: Float,
    translationY: Float,
    padding: Float,
    minX: Float,
    maxX: Float,
    minY: Float,
    maxY: Float
) {
    val gridColor = Color(0xFFE0E0E0)
    val gridStroke = Stroke(width = 1.dp.toPx())

    // Vertical grid lines
    for (i in 0..10) {
        val x = translationX + padding + (i / 10f) * chartWidth
        drawLine(
            color = gridColor,
            start = Offset(x, translationY + padding),
            end = Offset(x, translationY + padding + chartHeight),
            strokeWidth = gridStroke.width
        )
    }

    // Horizontal grid lines
    for (i in 0..10) {
        val y = translationY + padding + (i / 10f) * chartHeight
        drawLine(
            color = gridColor,
            start = Offset(translationX + padding, y),
            end = Offset(translationX + padding + chartWidth, y),
            strokeWidth = gridStroke.width
        )
    }
}

fun DrawScope.drawSpline(points: List<Offset>) {
    if (points.size < 2) return

    val path = Path()
    val controlPoints = calculateControlPoints(points)

    path.moveTo(points[0].x, points[0].y)

    for (i in 1 until points.size) {
        val prev = points[i - 1]
        val curr = points[i]
        val cp1 = controlPoints[2 * i - 2]
        val cp2 = controlPoints[2 * i - 1]

        path.cubicTo(
            prev.x + cp1.x, prev.y + cp1.y,
            curr.x + cp2.x, curr.y + cp2.y,
            curr.x, curr.y
        )
    }

    drawPath(
        path = path,
        color = Color(0xFF2196F3),
        style = Stroke(
            width = 3.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

fun DrawScope.drawAxes(
    chartWidth: Float,
    chartHeight: Float,
    translationX: Float,
    translationY: Float,
    padding: Float
) {
    val axisColor = Color(0xFF666666)
    val axisStroke = Stroke(width = 2.dp.toPx())

    // X-axis
    drawLine(
        color = axisColor,
        start = Offset(translationX + padding, translationY + padding + chartHeight),
        end = Offset(translationX + padding + chartWidth, translationY + padding + chartHeight),
        strokeWidth = axisStroke.width
    )

    // Y-axis
    drawLine(
        color = axisColor,
        start = Offset(translationX + padding, translationY + padding),
        end = Offset(translationX + padding, translationY + padding + chartHeight),
        strokeWidth = axisStroke.width
    )
}

fun calculateControlPoints(points: List<Offset>): List<Offset> {
    val n = points.size
    if (n < 2) return emptyList()

    val controlPoints = mutableListOf<Offset>()

    // For each segment, calculate two control points
    for (i in 0 until n - 1) {
        val p0 = if (i > 0) points[i - 1] else points[i]
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = if (i < n - 2) points[i + 2] else points[i + 1]

        // Calculate control points using Catmull-Rom spline
        val tension = 0.3f

        val cp1x = p1.x + (p2.x - p0.x) * tension / 3f
        val cp1y = p1.y + (p2.y - p0.y) * tension / 3f

        val cp2x = p2.x - (p3.x - p1.x) * tension / 3f
        val cp2y = p2.y - (p3.y - p1.y) * tension / 3f

        controlPoints.add(Offset(cp1x - p1.x, cp1y - p1.y))
        controlPoints.add(Offset(cp2x - p2.x, cp2y - p2.y))
    }

    return controlPoints
}

// Usage example
@Composable
fun App() {
    MaterialTheme {
        SplineChartScreen()
    }
}