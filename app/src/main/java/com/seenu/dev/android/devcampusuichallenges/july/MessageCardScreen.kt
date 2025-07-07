package com.seenu.dev.android.devcampusuichallenges.july

import android.R.attr.fontFamily
import android.R.attr.fontWeight
import android.R.attr.text
import android.R.id.message
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.devcampusuichallenges.R

@Composable
fun MessageCardScreen() {
    JulyTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                var selectedStatus by remember {
                    mutableStateOf(Message.Status.READ)
                }

                MessageBubble(
                    message = Message.DEFAULT.copy(status = selectedStatus), modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                MessageStatusChanger(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    selectedStatus = selectedStatus,
                ) {
                    selectedStatus = it
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageBubblePreview() {
    JulyTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            (MessageBubble(
                message = Message.DEFAULT
            ))
        }
    }
}

@Composable
fun MessageBubble(modifier: Modifier = Modifier, message: Message) {
    Row(modifier = modifier) {
        val shape = RoundedCornerShape(percent = 50)
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .size(24.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = shape)
                .clip(shape)
                .align(Alignment.Bottom),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message.sender[0].toString(),
                fontSize = 15.sp,
                fontFamily = Urbanist,
                color = MaterialTheme.colorScheme.onSurfaceAlt,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 8.dp, end = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface50,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = message.sender,
                    modifier = Modifier
                        .weight(1F)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 14.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "1 day ago",
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .3F),
                )
            }
            Text(
                text = message.message,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                fontSize = 16.sp,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val (icon, tint) = when (message.status) {
                    Message.Status.SENT -> R.drawable.icon_unread to MaterialTheme.colorScheme.onSurfaceVariant
                    Message.Status.DELIVERED -> R.drawable.icon_read to MaterialTheme.colorScheme.onSurfaceVariant
                    Message.Status.READ -> R.drawable.icon_read to MaterialTheme.colorScheme.blue
                }

                Icon(
                    painter = painterResource(id = icon),
                    modifier = Modifier.size(16.dp),
                    contentDescription = null,
                    tint = tint
                )

                Text(
                    text = stringResource(message.status.stringRes),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(start = 2.dp),
                    fontSize = 11.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.SemiBold,
                    color = tint
                )
            }
        }
    }
}

data class Message constructor(
    val sender: String,
    val message: String,
    val status: Status
) {

    companion object {
        val DEFAULT = Message(
            "DreamSyntaxHiker",
            "Iʼll send the draft tonight.I spent 9 months building what I thought would be a simple app to help people learn new languages through short conversations. I poured my evenings and weekends into it, but the launch was... underwhelming. ",
            status = Message.Status.READ
        )
    }

    enum class Status(@StringRes val stringRes: Int) {
        SENT(R.string.sent),
        DELIVERED(R.string.delivered),
        READ(R.string.read)
    }

}

@Composable
fun MessageStatusChanger(
    modifier: Modifier,
    selectedStatus: Message.Status,
    onStatusChange: (Message.Status) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        for (entry in Message.Status.entries) {
            val iconRes = if (entry == Message.Status.SENT) {
                R.drawable.icon_unread
            } else {
                R.drawable.icon_read
            }
            MessageStatusRow(
                icon = iconRes,
                title = stringResource(entry.stringRes),
                isSelected = entry == selectedStatus,
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .clickable {
                        onStatusChange(entry)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun MessageStatusRowPreview() {
    JulyTheme {
        MessageStatusRow(
            icon = R.drawable.icon_read,
            title = "Read",
            isSelected = false,
            modifier = Modifier.fillMaxWidth(0.7F)
        )
    }
}

@Composable
fun MessageStatusRow(
    modifier: Modifier = Modifier,
    @DrawableRes
    icon: Int,
    title: String,
    isSelected: Boolean = false
) {
    val (textColor, borderColor) = if (!isSelected) {
        MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant to MaterialTheme.colorScheme.surface50
    }

    Box(
        modifier = modifier
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = borderColor
            )
            .padding(horizontal = 8.dp, vertical = 4.dp), contentAlignment = Alignment.Center
    ) {
        Row {

            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .size(18.dp),
                contentDescription = null,
                tint = textColor
            )

            Text(
                text = stringResource(R.string.mark_as, title),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                fontSize = 16.sp,
                fontFamily = Urbanist,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        }
    }
}