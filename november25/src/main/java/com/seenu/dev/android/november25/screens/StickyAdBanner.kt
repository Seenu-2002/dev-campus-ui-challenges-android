package com.seenu.dev.android.november25.screens

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.components.CustomButton
import com.seenu.dev.android.november25.theme.HostGrotesk
import com.seenu.dev.android.november25.theme.StickyBannerTheme
import com.seenu.dev.android.november25.theme.textAlt
import com.seenu.dev.android.november25.theme.textOnDiscount

@Preview
@Composable
private fun StickyAdBannerPreview() {
    StickyBannerTheme {
        StickyAdBanner()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickyAdBanner() {
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                title = {
                    Text(
                        text = "TechDeals",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontFamily = HostGrotesk,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        var showBanner by remember { mutableStateOf(true) }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(2) { index ->
                StickyBannerProductCard(
                    modifier = Modifier,
                    product = stickyBannerProducts[index]
                )
            }
            if (showBanner) {
                stickyHeader {
                    StickyBanner(modifier = Modifier.animateItem(), onClose = {
                        showBanner = false
                    })
                }
            }
            items(stickyBannerProducts.size - 2 + stickyBannerProducts.size) { index ->
                StickyBannerProductCard(
                    modifier = Modifier,
                    product = stickyBannerProducts[(index + 2) % stickyBannerProducts.size]
                )
            }
        }
    }
}

@Preview
@Composable
private fun StickyBannerProductCardPreview() {
    StickyBannerTheme {
        StickyBannerProductCard(
            product = StickyBannerProduct(
                id = 1,
                name = "Google Pixel 9 Pro",
                price = 999.0,
                imageRes = R.drawable.google_pixel_9_pro
            )
        )
    }
}

@Composable
fun StickyBannerProductCard(
    modifier: Modifier = Modifier,
    product: StickyBannerProduct
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = product.name,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            fontFamily = HostGrotesk,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$${product.price}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = HostGrotesk,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
        )
    }
}

data class StickyBannerProduct constructor(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
)

private val stickyBannerProducts = listOf(
    StickyBannerProduct(
        id = 1,
        name = "Google Pixel 9 Pro",
        price = 999.0,
        imageRes = R.drawable.google_pixel_9_pro
    ),
    StickyBannerProduct(
        id = 2,
        name = "Google Pixel 9",
        price = 799.0,
        imageRes = R.drawable.google_pixel_9
    ),
    StickyBannerProduct(
        id = 3,
        name = "Google Pixel 8 Pro",
        price = 899.0,
        imageRes = R.drawable.google_pixel_8_pro
    ),
    StickyBannerProduct(
        id = 4,
        name = "Google Pixel 8",
        price = 699.0,
        imageRes = R.drawable.google_pixel_8
    ),
    StickyBannerProduct(
        id = 5,
        name = "Google Pixel Fold",
        price = 1799.0,
        imageRes = R.drawable.google_pixel_fold
    ),
    StickyBannerProduct(
        id = 6,
        name = "Google Pixel Tablet",
        price = 499.0,
        imageRes = R.drawable.google_pixel_tablet
    ),
    StickyBannerProduct(
        id = 7,
        name = "Google Pixel Watch 2",
        price = 349.0,
        imageRes = R.drawable.google_pixel_watch_2
    ),
    StickyBannerProduct(
        id = 8,
        name = "Google Pixel Buds Pro",
        price = 199.0,
        imageRes = R.drawable.google_pixel_buds_pro
    ),
    StickyBannerProduct(
        id = 9,
        name = "Google Nest Hub (2nd Gen)",
        price = 99.0,
        imageRes = R.drawable.google_nest_hub_2_gen
    ),
    StickyBannerProduct(
        id = 10,
        name = "Google Nest Audio",
        price = 99.0,
        imageRes = R.drawable.google_nest_audio
    )
)

@Preview
@Composable
private fun StickyBannerPreview() {
    StickyBannerTheme {
        StickyBanner() {}
    }
}

@Composable
fun StickyBanner(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
    ) {
        Image(
            painter = painterResource(R.drawable.banner_bg),
            contentDescription = "Sticky Banner Background",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 16.dp),
        ) {
            CustomButton(
                onClick = onClose, modifier = Modifier
                    .align(Alignment.End),
                drawBorder = false
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.textOnDiscount
                )
            }
            Text(
                text = "Black Friday Deals",
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.textOnDiscount
            )
            Text(
                text = "-50%",
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 44.sp,
                color = MaterialTheme.colorScheme.textAlt
            )
        }
    }
}