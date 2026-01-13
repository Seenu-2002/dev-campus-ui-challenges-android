package com.seenu.dev.android.december25.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.seenu.dev.android.december25.R
import com.seenu.dev.android.december25.state.Font
import com.seenu.dev.android.december25.state.FontSize
import com.seenu.dev.android.december25.state.PopupOptions
import com.seenu.dev.android.december25.state.TextColor
import com.seenu.dev.android.december25.theme.DecemberTheme
import com.seenu.dev.android.december25.theme.Montserrat
import com.seenu.dev.android.december25.theme.PTSans
import kotlin.math.max

@Preview
@Composable
private fun EditorActionBarPreview() {
    DecemberTheme {
        EditorActionBar()
    }
}

@Composable
fun EditorActionBar(
    modifier: Modifier = Modifier,
    isBold: Boolean = false,
    isItalic: Boolean = false,
    isUnderline: Boolean = false,
    selectedColor: TextColor = TextColor.COLOR_2,
    selectedFont: Font = Font.PT_SANS,
    selectedFontSize: FontSize = FontSize.SMALL,
    onBoldSelected: (Boolean) -> Unit = {},
    onItalicSelected: (Boolean) -> Unit = {},
    onUnderlineSelected: (Boolean) -> Unit = {},
    onTextColorSelected: (TextColor) -> Unit = {},
    onFontSelected: (Font) -> Unit = {},
    onFontSizeSelected: (FontSize) -> Unit = {},
    onRevert: () -> Unit = {},
) {
    var showPopupFor: PopupOptions? by remember {
        mutableStateOf(null)
    }

    Row(
        modifier
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            )
            .horizontalScroll(rememberScrollState())
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EditorButton(
            isSelected = isBold,
            onSelected = { onBoldSelected(!isBold) },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_bold),
                    contentDescription = null,
                )
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        EditorButton(
            isSelected = isItalic,
            onSelected = { onItalicSelected(!isItalic) },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_italic),
                    contentDescription = null,
                )
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        EditorButton(
            isSelected = isUnderline,
            onSelected = { onUnderlineSelected(!isUnderline) },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_underline),
                    contentDescription = null,
                )
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        EditorButtonWithIcon(
            showPopup = showPopupFor == PopupOptions.TEXT_COLOR,
            onSelected = {
                showPopupFor = if (showPopupFor == PopupOptions.TEXT_COLOR) {
                    null
                } else {
                    PopupOptions.TEXT_COLOR
                }
            },
            popupContent = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (color in TextColor.entries) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color.background,
                                    shape = MaterialTheme.shapes.small
                                )
                                .let {
                                    if (color.border != null) {
                                        it.border(
                                            width = 1.dp,
                                            color = color.border,
                                            shape = MaterialTheme.shapes.small
                                        )
                                    } else {
                                        it
                                    }
                                }
                                .clickable {
                                    onTextColorSelected(color)
                                    showPopupFor = null
                                }
                        )
                    }
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = selectedColor.background,
                            shape = MaterialTheme.shapes.small
                        )
                        .border(
                            2.dp, color = Color(0xFF0D1040),
                            shape = MaterialTheme.shapes.small
                        )
                )
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        EditorButtonWithIcon(
            showPopup = showPopupFor == PopupOptions.FONT,
            onSelected = {
                showPopupFor = if (showPopupFor == PopupOptions.FONT) {
                    null
                } else {
                    PopupOptions.FONT
                }
            },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_textcolor),
                    contentDescription = null,
                )
            },
            popupContent = {
                Column(modifier = Modifier) {
                    Text(
                        text = "Montserrat",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color(0xFF0D1040),
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                onFontSelected(Font.MONTSERRAT)
                                showPopupFor = null
                            }
                    )
                    Text(
                        text = "PT Sans",
                        fontFamily = PTSans,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF0D1040),
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                onFontSelected(Font.PT_SANS)
                                showPopupFor = null
                            }
                    )
                }
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        EditorButtonWithIcon(
            showPopup = showPopupFor == PopupOptions.FONT_SIZE,
            onSelected = {
                showPopupFor = if (showPopupFor == PopupOptions.FONT_SIZE) {
                    null
                } else {
                    PopupOptions.FONT_SIZE
                }
            },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_font),
                    contentDescription = null,
                )
            },
            popupContent = {
                Column(modifier = Modifier) {
                    Text(
                        text = "Small",
                        fontSize = 12.sp,
                        color = Color(0xFF0D1040),
                        fontFamily = Montserrat,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                onFontSizeSelected(FontSize.SMALL)
                                showPopupFor = null
                            }
                    )
                    Text(
                        text = "Medium",
                        fontSize = 12.sp,
                        color = Color(0xFF0D1040),
                        fontFamily = Montserrat,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                onFontSizeSelected(FontSize.MEDIUM)
                                showPopupFor = null
                            }
                    )
                    Text(
                        text = "Large",
                        fontSize = 12.sp,
                        color = Color(0xFF0D1040),
                        fontFamily = Montserrat,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                onFontSizeSelected(FontSize.LARGE)
                                showPopupFor = null
                            }
                    )
                }
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        EditorButton(
            isSelected = false,
            onSelected = { onRevert() },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_revert),
                    contentDescription = null,
                )
            },
        )
    }
}

@Composable
fun EditorButton(
    isSelected: Boolean,
    onSelected: (Boolean) -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (isSelected) Color(0xFFEFEFF0) else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onSelected(!isSelected)
            }
    ) {
        content()
    }
}

@Composable
fun EditorButtonWithIcon(
    showPopup: Boolean,
    onSelected: (Boolean) -> Unit,
    content: @Composable () -> Unit,
    popupContent: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var offset by remember {
        mutableStateOf(IntOffset.Zero)
    }
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var popupContentSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val space = with(LocalDensity.current) {
        12.dp.roundToPx()
    }
    if (showPopup) {
        Popup(
            offset = IntOffset(
                max(0, offset.x - (popupContentSize.width / 2) + (size.width / 2)),
                offset.y - (popupContentSize.height) - space
            ),
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFFFFFFF), shape = MaterialTheme.shapes.small)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFBFC1E2),
                        shape = MaterialTheme.shapes.small
                    )
                    .onGloballyPositioned {
                        popupContentSize = it.size
                    }
                    .padding(vertical = 6.dp, horizontal = 12.dp)
            ) {
                popupContent()
            }
        }
    }

    Row(
        modifier = modifier
            .background(
                color = if (showPopup) Color(0xFFEFEFF0) else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onSelected(!showPopup)
            }
            .onGloballyPositioned {
                offset = it.positionInParent().let { position ->
                    IntOffset(
                        x = position.x.toInt(),
                        y = position.y.toInt()
                    )
                }
                size = it.size
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()

        val icon = if (showPopup) {
            R.drawable.ic_arrow_up
        } else {
            R.drawable.ic_arrow_down
        }
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}