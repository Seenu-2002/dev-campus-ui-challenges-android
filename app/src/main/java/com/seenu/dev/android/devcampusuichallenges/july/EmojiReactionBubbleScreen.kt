package com.seenu.dev.android.devcampusuichallenges.july

import android.R.attr.fontFamily
import android.R.attr.text
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.devcampusuichallenges.july.theme.JulyTheme
import com.seenu.dev.android.devcampusuichallenges.july.theme.Urbanist
import com.seenu.dev.android.devcampusuichallenges.july.theme.backgroundGradientEnd
import com.seenu.dev.android.devcampusuichallenges.july.theme.blue12
import com.seenu.dev.android.devcampusuichallenges.july.theme.surface30
import com.seenu.dev.android.devcampusuichallenges.july.theme.surface50

@Composable
fun EmojiReactionBubbleScreen() {
    JulyTheme {
        val emojis =
            listOf("\uD83D\uDC4D", "\uD83D\uDE02", "\uD83D\uDE2E", "❤\uFE0F", "\uD83D\uDE22")
        val reactions = remember {
            mutableStateListOf<Reaction>()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.backgroundGradientEnd
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                EmojiRow(
                    emojis = emojis,
                ) { emoji ->
                    for ((index, reaction) in reactions.withIndex()) {
                        if (emoji == reaction.emoji) {
                            reactions[index] = reaction.copy(count = reaction.count + 1)
                            return@EmojiRow
                        }
                    }

                    reactions.add(
                        Reaction(
                            emoji = emoji,
                            count = 1
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                ReactionText(
                    text = "Iʼll send the draft tonight.",
                    reactions = reactions
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReactionTextPreview() {
    val reactions = listOf(
        Reaction("❤\uFE0F", 1),
        Reaction("👍", 2),
        Reaction("😂", 3)
    )

    JulyTheme {
        ReactionText(text = "Iʼll send the draft tonight.", reactions = reactions)
    }
}

@Composable
fun ReactionText(modifier: Modifier = Modifier, text: String, reactions: List<Reaction>) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface50,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            )
            .animateContentSize()
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = text,
            fontFamily = Urbanist,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        FlowRow(modifier = Modifier) {
            for (reaction in reactions) {
                ReactionCounter(
                    modifier = Modifier.padding(4.dp),
                    reaction = reaction
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReactionCounterPreview() {
    val reaction = Reaction(
        "❤\uFE0F",
        1
    )
    JulyTheme {
        ReactionCounter(reaction = reaction)
    }
}

@Composable
fun ReactionCounter(modifier: Modifier = Modifier, reaction: Reaction) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.blue12, shape = RoundedCornerShape(8.dp))
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            text = reaction.emoji,
            fontFamily = Urbanist,
            fontSize = 15.sp
        )
        if (reaction.count > 1) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = reaction.count.toString(),
                fontFamily = Urbanist,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

data class Reaction constructor(
    val emoji: String,
    val count: Int = 0
)

@Preview
@Composable
private fun EmojiRowPreview() {
    JulyTheme {
        val emojis = listOf("😀", "😂", "😍", "😎", "🤔", "😢", "😡")
        EmojiRow(emojis = emojis) {

        }
    }
}

@Composable
fun EmojiRow(
    modifier: Modifier = Modifier,
    emojis: List<String>,
    onEmojiTapped: (emoji: String) -> Unit
) {

    val shape = RoundedCornerShape(percent = 50)
    FlowRow(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface30,
                shape = shape
            )
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.surface50,
                shape = shape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        for (emoji in emojis) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    )
                    .clickable {
                        onEmojiTapped(emoji)
                    },
                text = emoji,
                fontSize = 18.sp,
                fontFamily = Urbanist
            )
        }
    }
}