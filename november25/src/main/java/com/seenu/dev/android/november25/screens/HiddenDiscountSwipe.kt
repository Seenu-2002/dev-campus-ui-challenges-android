package com.seenu.dev.android.november25.screens

import android.content.ClipData
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.components.CustomButton
import com.seenu.dev.android.november25.state.Product
import com.seenu.dev.android.november25.theme.HostGrotesk
import com.seenu.dev.android.november25.theme.NovemberTheme
import com.seenu.dev.android.november25.theme.discount
import com.seenu.dev.android.november25.theme.outlineAlt
import com.seenu.dev.android.november25.theme.snackBar
import com.seenu.dev.android.november25.theme.textAlt
import com.seenu.dev.android.november25.theme.textDisabled
import com.seenu.dev.android.november25.theme.textOnDiscount
import com.seenu.dev.android.november25.theme.textPrimary
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview
@Composable
private fun HiddenDiscountSwipePreview() {
    NovemberTheme {
        HiddenDiscountSwipe()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiddenDiscountSwipe(
    onNavigateBack: () -> Unit = {}
) {

    var appliedDiscountCode by remember {
        mutableStateOf<String?>(null)
    }
    var discountCode by remember {
        mutableStateOf("")
    }
    var isPromoCodeRevealed by remember {
        mutableStateOf(false)
    }

    var product by remember(appliedDiscountCode) {
        if (discountCode == "BF2025") {
            mutableStateOf(
                Product(
                    id = "1",
                    name = "Google Pixel Buds Pro",
                    description = "Noise-cancelling wireless earbuds with rich sound and long battery life.",
                    price = 199.0,
                    countInCart = 1,
                    imageRes = R.drawable.img_google_buds,
                    discount = 0.25
                )
            )
        } else {
            mutableStateOf(
                Product(
                    id = "1",
                    name = "Google Pixel Buds Pro",
                    description = "Noise-cancelling wireless earbuds with rich sound and long battery life.",
                    price = 199.0,
                    countInCart = 1,
                    imageRes = R.drawable.img_google_buds
                )
            )
        }
    }

    var showDiscountCodeInvalidError by remember(discountCode) {
        mutableStateOf(false)
    }

    val snackBarState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Cart",
                        fontFamily = HostGrotesk,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.textPrimary
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
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarState) { data ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(color = MaterialTheme.colorScheme.snackBar)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = data.visuals.message,
                        fontSize = 14.sp,
                        fontFamily = HostGrotesk,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.textAlt,
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val clipBoard = LocalClipboard.current
            ProductCardWithDiscount(
                product = product,
                onCodeCopied = {
                    scope.launch {
                        val clipData = ClipData.newPlainText("promo code", it)
                        val clipEntry = ClipEntry(clipData)
                        clipBoard.setClipEntry(clipEntry = clipEntry)
                        snackBarState.showSnackbar(
                            message = "Copied!"
                        )
                    }
                },
                isExpanded = isPromoCodeRevealed,
                onDecreaseCount = {
                    product = product.copy(countInCart = (product.countInCart - 1).coerceAtLeast(0))
                },
                onIncreaseCount = {
                    product = product.copy(countInCart = product.countInCart + 1)
                },
                onCollapsed = {
                    isPromoCodeRevealed = false
                },
                onExpanded = {
                    isPromoCodeRevealed = true
                }
            )

            Spacer(
                modifier = Modifier
                    .weight(1F)
            )

            DiscountTextBox(
                code = discountCode,
                isApplied = discountCode == appliedDiscountCode,
                onCodeChange = { discountCode = it.uppercase() },
                showInvalidError = showDiscountCodeInvalidError,
                onApply = {
                    if (discountCode == "BF2025") {
                        appliedDiscountCode = discountCode
                        showDiscountCodeInvalidError = false
                        isPromoCodeRevealed = false
                        scope.launch {
                            snackBarState.showSnackbar(
                                message = "Discount applied"
                            )
                        }
                    } else {
                        appliedDiscountCode = null
                        showDiscountCodeInvalidError = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextButton(
                onClick = {},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.textPrimary,
                    contentColor = MaterialTheme.colorScheme.textAlt
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Buy",
                    fontSize = 16.sp,
                    fontFamily = HostGrotesk,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )
        }

    }

}

@Preview
@Composable
private fun ProductCardPreview() {
    NovemberTheme {
        var product by remember() {
            mutableStateOf(
                Product(
                    id = "1",
                    name = "Google Pixel Buds Pro",
                    description = "Noise-cancelling wireless earbuds with rich sound and long battery life.",
                    price = 19.99,
                    countInCart = 2,
                    imageRes = R.drawable.img_google_buds,
                    discount = 0.25
                )
            )
        }
        ProductCard(
            product = product,
            onDecreaseCount = {
                product = product.copy(countInCart = (product.countInCart - 1).coerceAtLeast(0))
            },
            onIncreaseCount = {
                product = product.copy(countInCart = product.countInCart + 1)
            }
        )
    }
}

@Composable
fun ProductCardWithDiscount(
    modifier: Modifier = Modifier,
    product: Product,
    onCodeCopied: (code: String) -> Unit,
    isExpanded: Boolean,
    onIncreaseCount: () -> Unit = {},
    onDecreaseCount: () -> Unit = {},
    onExpanded: () -> Unit,
    onCollapsed: () -> Unit
) {
    var boxSize by remember {
        mutableStateOf(Size.Zero)
    }

    val offset = remember {
        Animatable(initialValue = 0F)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isExpanded, boxSize) {
        if (isExpanded) {
            offset.animateTo(boxSize.width)
        } else {
            offset.animateTo(0F)
        }
    }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                boxSize = it.size.toSize()
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            val newOffset = (offset.value + dragAmount).coerceIn(0F, boxSize.width)
                            offset.snapTo(newOffset)
                        }
                    },
                    onDragEnd = {
                        when {
                            offset.value >= boxSize.width / 2 -> {
                                scope.launch {
                                    offset.animateTo(boxSize.width)
                                    onExpanded()
                                }
                            }

                            else -> {
                                scope.launch {
                                    offset.animateTo(0F)
                                    onCollapsed()
                                }
                            }
                        }
                    }
                )
            }) {
        PromoCodeCard(modifier = Modifier.matchParentSize(), onCodeCopied = onCodeCopied)

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
        ) {
            ProductCard(
                product = product,
                onIncreaseCount = onIncreaseCount,
                onDecreaseCount = onDecreaseCount,
            )
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onIncreaseCount: () -> Unit = {},
    onDecreaseCount: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1F)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
        ) {

            Text(
                text = product.name,
                fontSize = 16.sp,
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.textPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = product.description,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.textDisabled,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemCounter(
                    count = product.countInCart,
                    onIncrease = onIncreaseCount,
                    onDecrease = onDecreaseCount
                )

                val actualPrice = if (product.discount != null) {
                    require(product.discount in 0.0..1.0) {
                        "Discount must be between 0.0 and 1.0"
                    }

                    product.price * (1 - product.discount)
                } else {
                    product.price
                }.toInt()
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = "$${actualPrice}",
                    fontSize = 24.sp,
                    fontFamily = HostGrotesk,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.textPrimary,
                    modifier = Modifier
                )

                if (product.discount != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$${product.price}",
                        fontSize = 16.sp,
                        fontFamily = HostGrotesk,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.textDisabled,
                        modifier = Modifier,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCounter(
    count: Int,
    min: Int = 1,
    max: Int = Int.MAX_VALUE,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isDecreaseEnabled = count > min
        CustomButton(
            onClick = onDecrease,
            enabled = isDecreaseEnabled,
            modifier = Modifier.padding(4.dp)
        ) {
            val tint = if (isDecreaseEnabled) {
                MaterialTheme.colorScheme.textPrimary
            } else {
                MaterialTheme.colorScheme.textDisabled
            }

            Icon(
                painter = painterResource(R.drawable.ic_minus),
                contentDescription = "Decrease",
                tint = tint,
                modifier = Modifier.size(14.dp)
            )
        }

        Text(
            text = "$count",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = HostGrotesk,
            color = MaterialTheme.colorScheme.textPrimary,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        val isIncreaseEnabled = count < max
        CustomButton(
            onClick = onIncrease,
            enabled = isIncreaseEnabled,
            modifier = Modifier.padding(4.dp)
        ) {
            val tint = if (isIncreaseEnabled) {
                MaterialTheme.colorScheme.textPrimary
            } else {
                MaterialTheme.colorScheme.textDisabled
            }

            Icon(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "Increase",
                tint = tint,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PromoCodeCardPreview() {
    NovemberTheme {
        PromoCodeCard() { }
    }
}

@Composable
fun PromoCodeCard(
    modifier: Modifier = Modifier,
    onCodeCopied: (code: String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD33F3F),
                        Color(0xFF7C1414),
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = "BLACK FRIDAY SALE",
                fontSize = 12.sp,
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.textOnDiscount
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "25%",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = HostGrotesk,
                color = MaterialTheme.colorScheme.textAlt
            )
            Text(
                text = "OFF",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = HostGrotesk,
                color = MaterialTheme.colorScheme.textAlt

            )
        }

        val circleColor = MaterialTheme.colorScheme.surface
        val dividerColor = MaterialTheme.colorScheme.outlineAlt
        Canvas(
            modifier = Modifier
                .width(20.dp)
                .fillMaxHeight()
        ) {
            val radius = 20.dp.toPx()
            val dashInterval = 12.dp.toPx()
            drawArc(
                color = circleColor,
                startAngle = 0F,
                sweepAngle = 180F,
                useCenter = true,
                topLeft = Offset(0F, -radius / 2),
                size = Size(radius, radius)
            )

            drawLine(
                color = dividerColor,
                strokeWidth = 1.dp.toPx(),
                start = Offset(radius / 2, radius),
                end = Offset(radius / 2, size.height - radius),
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(dashInterval, dashInterval), phase = 0F
                )
            )

            drawArc(
                color = circleColor,
                startAngle = 180F,
                sweepAngle = 180F,
                useCenter = true,
                topLeft = Offset(0F, size.height - radius / 2),
                size = Size(radius, radius)
            )
        }

        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .padding(12.dp), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val borderColor = MaterialTheme.colorScheme.outlineAlt
                Text(
                    text = "BF2025",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = HostGrotesk,
                    color = MaterialTheme.colorScheme.textAlt,
                    modifier = Modifier
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val strokeWidthHalf = strokeWidth / 2
                            val path = Path().apply {
                                moveTo(strokeWidthHalf, strokeWidthHalf)
                                lineTo(size.width, strokeWidthHalf)
                                moveTo(size.width, size.height - strokeWidthHalf)
                                lineTo(strokeWidthHalf, size.height - strokeWidthHalf)
                                lineTo(strokeWidthHalf, strokeWidthHalf)
                            }

                            drawPath(
                                path = path, style = Stroke(
                                    width = strokeWidth,
                                ),
                                color = borderColor
                            )
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        )
                )

                CustomButton(
                    onClick = { onCodeCopied("BF2025") },
                    drawBorder = false,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                        .fillMaxHeight()
                        .aspectRatio(1F)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_copy),
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.discount,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DiscountTextBoxPreview() {
    NovemberTheme {
        var code by remember {
            mutableStateOf("")
        }
        DiscountTextBox(
            code = code,
            onCodeChange = { code = it },
            onApply = { }
        )
    }
}

@Composable
fun DiscountTextBox(
    code: String,
    isApplied: Boolean = false,
    showInvalidError: Boolean = false,
    onCodeChange: (String) -> Unit,
    onApply: (code: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val borderColor = MaterialTheme.colorScheme.outline
            BasicTextField(
                value = code,
                onValueChange = onCodeChange,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = HostGrotesk,
                    color = MaterialTheme.colorScheme.textPrimary,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters
                ),
                modifier = Modifier
                    .weight(1F)
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val strokeWidthHalf = strokeWidth / 2
                        val path = Path().apply {
                            moveTo(strokeWidthHalf, strokeWidthHalf)
                            lineTo(size.width, strokeWidthHalf)
                            moveTo(size.width, size.height - strokeWidthHalf)
                            lineTo(strokeWidthHalf, size.height - strokeWidthHalf)
                            lineTo(strokeWidthHalf, strokeWidthHalf)
                        }

                        drawPath(
                            path = path, style = Stroke(
                                width = strokeWidth,
                            ),
                            color = borderColor
                        )
                    }
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                decorationBox = { innerTextField ->
                    if (code.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "Enter Promo Code",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = HostGrotesk,
                                color = MaterialTheme.colorScheme.textDisabled,
                            )
                        }
                    }
                    innerTextField()

                }
            )
            CustomButton(
                onClick = { onApply(code) },
                drawBorder = false,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.outline)
                    .fillMaxHeight()
                    .padding(horizontal = 22.dp, vertical = 8.dp)
            ) {
                val text = if (isApplied) {
                    "Applied"
                } else {
                    "Apply"
                }
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontFamily = HostGrotesk,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.textPrimary,
                )
            }
        }

        if (showInvalidError) {
            Text(
                text = "Invalid code",
                fontSize = 12.sp,
                fontFamily = HostGrotesk,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.discount,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}