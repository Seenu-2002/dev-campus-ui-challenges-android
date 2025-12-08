package com.seenu.dev.android.november25.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.state.compareProducts
import com.seenu.dev.android.november25.theme.HostGrotesk
import com.seenu.dev.android.november25.theme.NovemberTheme
import com.seenu.dev.android.november25.theme.background3
import com.seenu.dev.android.november25.theme.brand3
import com.seenu.dev.android.november25.theme.discount3
import com.seenu.dev.android.november25.theme.textAlt
import com.seenu.dev.android.november25.theme.textDisabled3
import com.seenu.dev.android.november25.theme.textPrimary
import kotlinx.serialization.Serializable
import java.util.UUID

@Preview
@Composable
private fun LongPressCompareListScreenPreview() {
    NovemberTheme {
        LongPressCompareListScreen(onCompare = { _, _ ->

        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LongPressCompareListScreen(
    onCompare: (String, String) -> Unit,
    onNavigateBack: () -> Unit = {}
) {

    val selectedProducts = rememberSaveable(
        saver = listSaver(
            save = {
                it.map { product -> product.id }
            },
            restore = {
                (it.map { id -> compareProducts.first { product -> product.id == id } }).toMutableStateList()
            }
        )) { mutableStateListOf<LongPressCompareProduct>() }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                if (selectedProducts.isNotEmpty()) {
                    Text(
                        text = "${selectedProducts.size} items selected",
                        fontFamily = HostGrotesk,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.textDisabled3
                    )
                } else {
                    Text(
                        text = "TechStore",
                        fontFamily = HostGrotesk,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(
                            R.drawable.ic_arrow_left
                        ),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.textPrimary
                    )
                }
            },
            actions = {
                if (selectedProducts.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onCompare(
                                selectedProducts[0].id,
                                selectedProducts[1].id
                            )
                        },
                        enabled = selectedProducts.size == 2,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContentColor = MaterialTheme.colorScheme.textDisabled3,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Icon(
                            painter = painterResource(
                                R.drawable.ic_scale
                            ),
                            contentDescription = "Compare products"
                        )
                    }
                } else {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background3
            )
        )
    }, containerColor = MaterialTheme.colorScheme.background3) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(compareProducts.size) { index ->
                val isSelected = selectedProducts.contains(compareProducts[index])
                LongPressComparePreviewProductCard(
                    product = compareProducts[index],
                    onLongPress = {
                        if (selectedProducts.contains(compareProducts[index])) {
                            selectedProducts.remove(compareProducts[index])
                        } else {
                            if (selectedProducts.size >= 2) {
                                selectedProducts.removeLast()
                            }
                            selectedProducts.add(compareProducts[index])
                        }
                    },
                    modifier = Modifier
                        .dropShadow(
                            shape = MaterialTheme.shapes.medium,
                            shadow = Shadow(
                                radius = 4.dp,
                                color = Color(0x0A041221),
                                offset = DpOffset(0.dp, 4.dp),
                                spread = 0.dp
                            )
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .let {
                            if (isSelected) {
                                it.border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.brand3,
                                    shape = MaterialTheme.shapes.medium
                                )
                            } else {
                                it
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun LongPressComparePreviewProductCard(
    product: LongPressCompareProduct,
    modifier: Modifier = Modifier,
    showBorderAroundImage: Boolean = false,
    onLongPress: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress()
                    }
                )
            }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
                .clip(shape = MaterialTheme.shapes.medium)
                .let {
                    if (showBorderAroundImage) {
                        it.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = MaterialTheme.shapes.medium
                        )
                    } else {
                        it
                    }
                }
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
            )

            if (product.salePrice != null) {
                val percentage =
                    ((product.price - product.salePrice).toFloat() / product.price.toFloat() * 100).toInt()
                Text(
                    text = "-$percentage%",
                    fontFamily = HostGrotesk,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.textAlt,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.discount3,
                            shape = CircleShape
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
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
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            if (product.salePrice != null) {
                Text(
                    text = "$${product.salePrice}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = HostGrotesk,
                    color = MaterialTheme.colorScheme.discount3,
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(
                    text = "$${product.price}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    fontFamily = HostGrotesk,
                    color = MaterialTheme.colorScheme.textDisabled3,
                    textDecoration = TextDecoration.LineThrough,
                )
            } else {
                Text(
                    text = "$${product.price}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = HostGrotesk,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        TextButton(
            onClick = {

            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.textAlt,
                containerColor = MaterialTheme.colorScheme.brand3
            ),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "Buy",
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.textAlt
            )
        }
    }
}

@Serializable
data class LongPressCompareProduct constructor(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    val imageRes: Int,
    val salePrice: Int? = null,
    val displayInches: Double,
    val mainCameraMP: Int,
    val frontCameraMP: Int,
    val processor: String,
    val ramGB: Int,
    val storageGB: Int,
    val batteryMAh: Int
)