package com.seenu.dev.android.february26.shared_valentine.receiver

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seenu.dev.android.february26.R
import com.seenu.dev.android.february26.shared_valentine.SharedValentineTheme
import com.seenu.dev.android.february26.shared_valentine.StackSans
import com.seenu.dev.android.february26.shared_valentine.components.SharedValentineCard
import com.seenu.dev.android.february26.shared_valentine.components.ValentineGift
import com.seenu.dev.android.february26.shared_valentine.surfaceActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedValentineReceiver(
    receivedItem: ValentineGift? = null,
    onReceiveHandled: () -> Unit = {}
) {

    val viewModel: SharedValentineReceiverViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current.applicationContext
    LaunchedEffect(Unit) {
        viewModel.getReceivedItems(context = context)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Your Valentines",
                    fontSize = 28.sp,
                    fontFamily = StackSans,
                    fontWeight = FontWeight.SemiBold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopStart
        ) {
            val gifts = uiState.receivedItems

            if (gifts.isEmpty()) {
                WaitingForValentineCard()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.receivedItems.size) { index ->
                        SharedValentineCard(
                            imageRes = uiState.receivedItems.elementAt(index).imageRes,
                            showBorderInInactiveState = true,
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = receivedItem != null,
                enter = scaleIn(tween(1_000)),
                exit = scaleOut(tween(1_000))
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ValentineGiftReceivedCard(
                        valentine = receivedItem ?: return@Surface,
                        onDismiss = onReceiveHandled,
                        onSave = {
                            viewModel.saveReceivedValentine(receivedItem)
                            onReceiveHandled()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WaitingForValentineCard_Preview() {
    SharedValentineTheme {
        WaitingForValentineCard()
    }
}

@Composable
fun WaitingForValentineCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceActive.copy(alpha = .5F),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_mail),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Waiting for Valentine...",
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StackSans,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview
@Composable
private fun ValentineGiftReceivedCard_Preview() {
    SharedValentineTheme {
        ValentineGiftReceivedCard(valentine = ValentineGift.TEDDY)
    }
}

@Composable
fun ValentineGiftReceivedCard(
    valentine: ValentineGift,
    onDismiss: () -> Unit = {},
    onSave: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
                .border(
                    width = 1.dp, color = MaterialTheme.colorScheme.surfaceActive,
                    shape = RoundedCornerShape(36.dp)
                )
                .padding(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceActive,
                        shape = RoundedCornerShape(30.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(valentine.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F)
                        .graphicsLayer {
                            scaleX = 1.2F
                            scaleY = 1.2F
                        }
                )
            }
        }
        Text(
            text = "You've received a Valentine!",
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold,
            fontFamily = StackSans
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.surface
                    )
                    .weight(1F),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(
                    text = "Dismiss",
                    fontSize = 17.sp,
                    fontFamily = StackSans,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            TextButton(
                onClick = onSave,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = "Save",
                    fontSize = 17.sp,
                    fontFamily = StackSans,
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }
}