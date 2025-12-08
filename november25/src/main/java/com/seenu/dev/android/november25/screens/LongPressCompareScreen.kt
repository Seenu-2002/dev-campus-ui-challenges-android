package com.seenu.dev.android.november25.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.state.compareProducts
import com.seenu.dev.android.november25.theme.HostGrotesk
import com.seenu.dev.android.november25.theme.LongPressGestureTheme
import com.seenu.dev.android.november25.theme.background3
import com.seenu.dev.android.november25.theme.textDisabled3
import com.seenu.dev.android.november25.theme.textPrimary

@Preview
@Composable
private fun LongPressCompareScreenPreview() {
    LongPressGestureTheme {
        LongPressCompareScreen(
            productA = compareProducts.random(),
            productB = compareProducts.random(),
            onNavigateBack = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LongPressCompareScreen(
    productA: LongPressCompareProduct,
    productB: LongPressCompareProduct,
    onNavigateBack: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Comparison",
                    fontFamily = HostGrotesk,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background3
            )
        )
    }, containerColor = MaterialTheme.colorScheme.background3) { innerPadding ->
        CompareProducts(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            productA = productA,
            productB = productB
        )
    }
}

@Preview
@Composable
private fun CompareProductsPreview() {
    LongPressGestureTheme {
        CompareProducts(
            productA = compareProducts.random(),
            productB = compareProducts.random()
        )
    }
}

@Composable
fun CompareProducts(
    modifier: Modifier = Modifier,
    productA: LongPressCompareProduct,
    productB: LongPressCompareProduct
) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LongPressComparePreviewProductCard(
                productA,
                modifier = Modifier.weight(1f),
                showBorderAroundImage = true
            )
            Spacer(modifier = Modifier.width(12.dp))
            LongPressComparePreviewProductCard(
                productB,
                modifier = Modifier.weight(1f),
                showBorderAroundImage = true
            )
        }
        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )
        CompareDataRow(
            title = "Display",
            data1 = "${productA.displayInches}\"",
            data2 = "${productB.displayInches}\""
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )
        CompareDataRow(
            title = "Main Camera",
            data1 = "${productA.mainCameraMP}MP",
            data2 = "${productB.mainCameraMP}MP"
        )
        CompareDataRow(
            title = "Front Camera",
            data1 = "${productA.frontCameraMP}MP",
            data2 = "${productB.frontCameraMP}MP"
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )
        CompareDataRow(
            title = "Processor",
            data1 = productA.processor,
            data2 = productB.processor
        )
        CompareDataRow(
            title = "RAM",
            data1 = "${productA.ramGB}GB",
            data2 = "${productB.ramGB}GB"
        )
        CompareDataRow(
            title = "Font Camera",
            data1 = "${productA.frontCameraMP}MP",
            data2 = "${productB.frontCameraMP}MP"
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )
        CompareDataRow(
            title = "Battery (mAh)",
            data1 = "${productA.frontCameraMP} mAh",
            data2 = "${productB.frontCameraMP} mAh"
        )
    }
}

@Composable
fun ColumnScope.CompareDataRow(
    title: String,
    data1: String,
    data2: String
) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = title,
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = data1,
            fontFamily = HostGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(1F)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = data2,
            fontFamily = HostGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(1F)
        )
    }
}