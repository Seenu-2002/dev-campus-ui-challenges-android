package com.seenu.dev.android.january26

import android.R.attr.contentDescription
import android.R.attr.name
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.seenu.dev.android.january26.state.Destination
import com.seenu.dev.android.january26.theme.JakartaSans
import com.seenu.dev.android.january26.theme.JanuaryTheme
import com.seenu.dev.android.january26.theme.bgGallery
import com.seenu.dev.android.january26.theme.bgMainGradientEnd
import com.seenu.dev.android.january26.theme.bgMainGradientStart
import com.seenu.dev.android.january26.theme.textCard

@Preview
@Composable
private fun WinterTravelGalleryPreview() {
    JanuaryTheme {
        WinterTravelGallery()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinterTravelGallery(openDestination: (Destination) -> Unit = {}) {
    val destinations = Destination.entries

    Scaffold(
        modifier = Modifier
            .fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Winter Travel Gallery",
                        fontFamily = JakartaSans,
                        fontSize = 28.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.bgMainGradientStart,
                            MaterialTheme.colorScheme.bgMainGradientEnd
                        ),
                    )
                )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(destinations) { destination ->
                DestinationPreviewCard(
                    destination = destination,
                    onClick = { openDestination(destination) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun DestinationPreviewCardPreview() {
    JanuaryTheme {
        DestinationPreviewCard(
            destination = Destination.ALPS,
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun DestinationPreviewCard(
    destination: Destination,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.bgGallery,
                shape = MaterialTheme.shapes.medium
            )
            .aspectRatio(.83F)
    ) {
        Image(
            painter = painterResource(destination.imageRes),
            contentDescription = destination.name,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = destination.title,
                fontSize = 18.sp,
                lineHeight = 20.sp,
                fontFamily = JakartaSans,
                color = MaterialTheme.colorScheme.textCard,
                modifier = Modifier.weight(1F)
            )

            Box(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.bgGallery,
                        shape = CircleShape
                    )
                    .padding(3.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "Open $name",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}