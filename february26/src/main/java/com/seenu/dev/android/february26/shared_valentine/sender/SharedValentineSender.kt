package com.seenu.dev.android.february26.shared_valentine.sender

import android.app.sdksandbox.SandboxedSdk
import android.content.Context
import android.content.Intent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.february26.R
import com.seenu.dev.android.february26.shared_valentine.SharedValentineTheme
import com.seenu.dev.android.february26.shared_valentine.StackSans
import com.seenu.dev.android.february26.shared_valentine.buttonPrimaryDisabled
import com.seenu.dev.android.february26.shared_valentine.components.SharedValentineCard
import com.seenu.dev.android.february26.shared_valentine.components.ValentineGift
import com.seenu.dev.android.february26.shared_valentine.surfaceActive
import kotlinx.coroutines.launch
import kotlin.random.Random

@Preview
@Composable
private fun SharedValentineSender_Preview() {
    SharedValentineTheme {
        SharedValentineSender()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedValentineSender() {

    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Choose a Valentine", fontSize = 28.sp,
                    fontFamily = StackSans,
                    fontWeight = FontWeight.SemiBold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val items = ValentineGift.entries
            items(items.size) { index ->
                SharedValentineCard(
                    imageRes = items[index].imageRes,
                    isSelected = index == selectedIndex,
                    onClick = {
                        selectedIndex = index
                    }
                )
            }
            item(span = {
                GridItemSpan(3)
            }) {
                val context = LocalContext.current
                TextButton(
                    onClick = { sendGift(context, ValentineGift.entries[selectedIndex]) },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.buttonPrimaryDisabled,
                        disabledContentColor = MaterialTheme.colorScheme.background
                    ),
                    shape = MaterialTheme.shapes.medium,
                    enabled = selectedIndex != -1,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Send",
                        fontFamily = StackSans,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

fun sendGift(context: Context, gift: ValentineGift) {
    val intent = Intent(
        "com.seenu.dev.android.devcampusuichallenges.RECEIVE_VALENTINE_GIFT"
    ).apply {
        putExtra("gift", gift.name)
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}