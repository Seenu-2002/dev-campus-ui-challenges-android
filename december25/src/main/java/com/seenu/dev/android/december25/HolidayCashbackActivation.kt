package com.seenu.dev.android.december25

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seenu.dev.android.december25.theme.DecemberTheme
import com.seenu.dev.android.december25.theme.Montserrat
import com.seenu.dev.android.december25.theme.buttonPrimary
import com.seenu.dev.android.december25.theme.buttonPrimaryDisabled
import com.seenu.dev.android.december25.theme.surfaceBg
import com.seenu.dev.android.december25.theme.textPrimaryDark
import com.seenu.dev.android.december25.theme.textPrimaryWhite
import com.seenu.dev.android.december25.theme.textSecondary

@Preview
@Composable
private fun HolidayCashbackActivationPreview() {
    DecemberTheme {
        HolidayCashbackActivation()
    }
}

@Composable
fun HolidayCashbackActivation() {

    val snackBarState = remember {
        SnackbarHostState()
    }
    val viewModel: HolidayCashbackActivationViewModel = viewModel()
    val uiState: HolidayCashbackActivationUiState by viewModel.cardState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.cardStatus == CardStatus.ACTIVATED) {
            snackBarState.showSnackbar("")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceBg,
        snackbarHost = {
            SnackbarHost(hostState = snackBarState, modifier = Modifier.padding(bottom = 64.dp)) {
                CustomSnackBar(modifier = Modifier)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(42.dp))
            Image(
                painter = painterResource(R.drawable.img_gift_card),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Link your card to activate holiday cashback bonuses!",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.textPrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            CardNumberInputField(
                value = uiState.cardNumber,
                type = uiState.cardType,
                status = uiState.cardStatus,
                onValueChange = {
                    viewModel.onIntent(HolidayCashbackActivationIntent.OnCardNumberChange(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1F))
            Button(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.cardStatus != CardStatus.ACTIVATING && uiState.cardNumber.length == 16,
                onClick = {
                    val intent = when (uiState.cardStatus) {
                        CardStatus.ACTIVATED -> HolidayCashbackActivationIntent.OnActionDone
                        else -> HolidayCashbackActivationIntent.OnActivateCardClick
                    }
                    viewModel.onIntent(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.buttonPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.buttonPrimaryDisabled
                )
            ) {
                when (uiState.cardStatus) {
                    CardStatus.ACTIVATING -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.buttonPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Activating",
                            fontSize = 16.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.textPrimaryWhite
                        )
                    }

                    CardStatus.ACTIVATED -> {
                        Text(
                            text = "Done",
                            fontSize = 16.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.textPrimaryWhite
                        )
                    }

                    else -> {
                        Text(
                            text = "Activate",
                            fontSize = 16.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.textPrimaryWhite
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CustomSnackBarPreview() {
    DecemberTheme {
        CustomSnackBar(modifier = Modifier)
    }
}

@Composable
fun CustomSnackBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .border(
                1.dp,
                color = Color(0xFF159302),
                shape = MaterialTheme.shapes.medium
            )
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_checkmark),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Holiday Cashback Activated!",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.textPrimaryDark,
            modifier = Modifier.weight(1F)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun CardNumberInputField(
    value: String,
    type: CardType,
    status: CardStatus,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = if (status == CardStatus.ACTIVATION_ERROR) Color(0xFFEB001B) else Color(
                        0xFFE7D2BF
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = when (type) {
                CardType.VISA -> R.drawable.ic_visa
                CardType.MASTERCARD -> R.drawable.ic_mastercard
            }
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
                    .weight(1F),
                singleLine = true,
                visualTransformation = remember { CreditCardVisualTransformation() },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = "XXXX XXXX XXXX XXXX",
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.textSecondary,
                            textAlign = TextAlign.Start,
                        )
                    }
                    innerTextField()
                },
                textStyle = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.textPrimaryDark
                )
            )

            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(2.dp)
                        .clickable {
                            onValueChange("")
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                    )
                }
            }
        }

        if (status == CardStatus.ACTIVATION_ERROR) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Invalid card number",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFFEB001B),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

class CreditCardVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(16)

        val formatted = buildString {
            trimmed.forEachIndexed { index, char ->
                append(char)
                if ((index + 1) % 4 == 0 && index != trimmed.lastIndex) {
                    append(" ")
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (offset) {
                    in 1..4 -> offset
                    in 5..8 -> offset + 1
                    in 9..12 -> offset + 2
                    in 13..16 -> offset + 3
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when (offset) {
                    in 1..4 -> offset
                    in 5..9 -> offset - 1
                    in 10..14 -> offset - 2
                    in 15..19 -> offset - 3
                    else -> trimmed.length
                }
            }
        }

        return TransformedText(
            AnnotatedString(formatted),
            offsetMapping
        )
    }
}