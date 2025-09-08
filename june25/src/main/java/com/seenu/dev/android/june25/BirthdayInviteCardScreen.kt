package com.seenu.dev.android.june25

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.seenu.dev.android.june25.theme.Mali
import com.seenu.dev.android.june25.theme.Nunito

@Composable
fun BirthdayInviteCardScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF1A597B)),
        contentAlignment = Alignment.Center
    ) {

        BirthdayInviteCard(
            modifier = Modifier.fillMaxWidth(.95F)
        )

    }
}

@Composable
fun BirthdayInviteCard(modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(16.dp)

    ConstraintLayout(
        modifier = modifier
            .background(
                color = Color(0xFFFFF5EB),
                shape = shape
            )
            .clip(shape)
            .paint(
                painter = painterResource(R.drawable.birthday_decor_bg),
                contentScale = ContentScale.FillBounds
            )
    ) {
        val (container, rsvp) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(container) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(rsvp.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You’re invited!",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = Mali,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Join us for a birthday bash \uD83C\uDF89",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Mali,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = getAnnotatedString(name = "Date", "June 14, 2025"), style = MaterialTheme.typography.bodyMedium, fontFamily = Nunito)
            Text(text = getAnnotatedString(name = "Time", "3.00 PM"), fontFamily = Nunito)
            Text(
                text = getAnnotatedString(name = "Location", "Party Central, 123 Celebration Lane"),
                fontFamily = Nunito,
                textAlign = TextAlign.Center
            )
        }

        Text(
            modifier = Modifier.constrainAs(rsvp) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            text = "RSVP by June 9",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = Nunito
        )

    }
}

@Preview
@Composable
private fun BirthdayInviteCardPreview() {
    BirthdayInviteCard(
        modifier = Modifier
            .wrapContentHeight()
    )
}

@Composable
private fun getAnnotatedString(name: String, value: String): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(
            SpanStyle(
                fontFamily = Nunito,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF113345)
            )
        )
        append(name)
        append(": ")
        pushStyle(
            SpanStyle(
                fontFamily = Nunito,
                fontWeight = FontWeight.Normal,
                color = Color(0xCC113345)
            )
        )
        append(value)
        pop()
    }
}