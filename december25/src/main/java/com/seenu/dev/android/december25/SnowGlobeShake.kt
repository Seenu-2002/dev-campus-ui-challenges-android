package com.seenu.dev.android.december25

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seenu.dev.android.december25.theme.DecemberTheme
import com.seenu.dev.android.december25.theme.globeGradientEnd
import com.seenu.dev.android.december25.theme.globeGradientMiddle
import com.seenu.dev.android.december25.theme.globeGradientStart

@Preview
@Composable
private fun SnowGlobeShakePreview() {
    DecemberTheme {
        SnowGlobeShake()
    }
}

@Composable
fun SnowGlobeShake() {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.globeGradientStart,
                        MaterialTheme.colorScheme.globeGradientMiddle,
                        MaterialTheme.colorScheme.globeGradientEnd,
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        SnowGlobe(startAnimate = startAnimation)
    }
}

@Composable
fun SnowGlobe(startAnimate: Boolean, modifier: Modifier = Modifier) {

    Box(modifier = modifier
        .height(IntrinsicSize.Max)) {
        Image(
            painter = painterResource(R.drawable.img_globe_holder),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1F)
                .align(Alignment.Center)
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.8F)
                .padding(top = 8.dp)
        ) {
            val radius = size.minDimension / 2F
//            drawCircle(
//                color = Color.Black.copy(alpha = .5F),
//                radius = radius,
//                center = center
//            )
            val startX = center.x - radius
            val startY = center.y - radius
            val endX = center.x + radius
            val endY = center.y + radius

            val path = Path()
            path.addOval(
                Rect(
                    left = startX,
                    top = startY,
                    right = endX,
                    bottom = endY
                )
            )
            drawPath(
                path = path,
                color = Color.Black.copy(alpha = .5F)
            )
        }
    }

}