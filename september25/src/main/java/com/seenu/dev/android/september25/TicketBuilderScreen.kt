package com.seenu.dev.android.september25

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.september25.theme.Parkinsans
import com.seenu.dev.android.september25.theme.SeptemberTheme
import com.seenu.dev.android.september25.theme.lime
import com.seenu.dev.android.september25.theme.surfaceHigher
import com.seenu.dev.android.september25.theme.textDisabled
import com.seenu.dev.android.september25.theme.textPrimary
import com.seenu.dev.android.september25.theme.textSecondary

@Preview(showBackground = true)
@Composable
private fun TicketBuilderScreenPreview() {
    SeptemberTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TicketBuilderScreen()
        }
    }
}

@Composable
fun TicketBuilderScreen() {

    var selectedTicketType: TicketType? by remember {
        mutableStateOf(null)
    }
    var quantity by remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeDrawing
            )
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = stringResource(R.string.ticket_builder),
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Parkinsans,
                lineHeight = (60 * .9F).sp,
                fontSize = 60.sp
            )
            Text(
                text = stringResource(R.string.ticket_builder_subtitle),
                color = MaterialTheme.colorScheme.textSecondary,
                fontWeight = FontWeight.Normal,
                fontFamily = Parkinsans,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        TicketBuilderContent(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            selectedTicketType = selectedTicketType,
            quantity = quantity,
            onQuantityChanged = {
                quantity = it
            },
            onTypeSelected = {
                selectedTicketType = it
            }
        )
    }
}

@Composable
fun TicketBuilderContent(
    modifier: Modifier = Modifier,
    selectedTicketType: TicketType?,
    quantity: Int,
    onQuantityChanged: (Int) -> Unit,
    onTypeSelected: (TicketType) -> Unit = {}
) {

    Column(
        modifier = modifier.padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceHigher,
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 12.dp, vertical = 40.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.ticket_type),
                fontFamily = Parkinsans,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.textSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                for (type in TicketType.entries) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onTypeSelected(type)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = type == selectedTicketType, onClick = {
                                onTypeSelected(type)
                            }, colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.textPrimary,
                                unselectedColor = MaterialTheme.colorScheme.textPrimary
                            )
                        )
                        Text(
                            text = stringResource(type.getTitleRes()),
                            modifier = Modifier.weight(1F),
                            fontFamily = Parkinsans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.textPrimary
                        )
                        Text(
                            text = "$${type.price}",
                            fontFamily = Parkinsans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.textPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.quantity),
                fontFamily = Parkinsans,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.textSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))
            QuantitySelector(
                modifier = Modifier.fillMaxWidth(),
                quantity = quantity,
                onQuantityChanged
            )
            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.textPrimary)

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.total),
                    modifier = Modifier.weight(1F),
                    fontFamily = Parkinsans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.textPrimary
                )
                BasicText(
                    text = "$${(selectedTicketType?.price ?: 0) * quantity}",
                    autoSize = TextAutoSize.StepBased(
                        16.sp, 28.sp, 2.sp
                    ),
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = Parkinsans,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.textPrimary
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = selectedTicketType != null && quantity > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.lime,
                contentColor = MaterialTheme.colorScheme.textPrimary,
                disabledContentColor = MaterialTheme.colorScheme.textDisabled,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceHigher
            )
        ) {
            Text(
                text = stringResource(R.string.purchase),
                fontFamily = Parkinsans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
            )
        }
    }
}

@Preview
@Composable
private fun QuantitySelectorPreview() {
    SeptemberTheme {
        QuantitySelector(modifier = Modifier.fillMaxWidth(), quantity = (1..2).random()) {

        }
    }
}

@Composable
fun QuantitySelector(
    modifier: Modifier = Modifier,
    quantity: Int,
    onQuantityChanged: (Int) -> Unit
) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Button(
            modifier = Modifier.size(68.dp),
            contentPadding = PaddingValues(0.dp),
            shape = MaterialTheme.shapes.small,
            enabled = quantity > 1,
            onClick = {
                onQuantityChanged(quantity - 1)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.textPrimary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = MaterialTheme.colorScheme.textDisabled,
            )
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.ic_minus),
                contentDescription = "Decrease quantity"
            )
        }
        Text(
            text = "$quantity",
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center,
            fontFamily = Parkinsans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Button(
            modifier = Modifier.size(68.dp),
            contentPadding = PaddingValues(0.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {
                onQuantityChanged(quantity + 1)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.textPrimary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = MaterialTheme.colorScheme.textDisabled,
            )
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.ic_add),
                contentDescription = "Increase quantity"
            )
        }
    }
}

enum class TicketType(val price: Int) {
    STANDARD(40),
    VIP(70),
    BACKSTAGE(120)
}

fun TicketType.getTitleRes(): Int {
    return when (this) {
        TicketType.STANDARD -> R.string.type_standard
        TicketType.VIP -> R.string.type_vip
        TicketType.BACKSTAGE -> R.string.type_backstage
    }
}