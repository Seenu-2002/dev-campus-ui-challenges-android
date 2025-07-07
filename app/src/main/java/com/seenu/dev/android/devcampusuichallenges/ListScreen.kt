package com.seenu.dev.android.devcampusuichallenges

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.devcampusuichallenges.state.Challenge
import com.seenu.dev.android.devcampusuichallenges.state.ChallengeMonth
import com.seenu.dev.android.devcampusuichallenges.state.challenges
import com.seenu.dev.android.devcampusuichallenges.ui.theme.DevCampusUIChallengesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(onChallengeSelected: (Challenge) -> Unit) {

    var selectedMonth by rememberSaveable {
        mutableStateOf(ChallengeMonth.JULY_2025)
    }

    var challengeList by remember(selectedMonth) {
        mutableStateOf(challenges[selectedMonth] ?: emptyList())
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(selectedMonth.stringRes),
                    modifier = Modifier
                        .padding(16.dp),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown, contentDescription = "Select Month",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                items(challengeList) { challenge ->
                    ChallengeRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                onChallengeSelected(challenge)
                            },
                        challenge = challenge
                    )
                }
            }
        }
    }
}

@Composable
fun ChallengeRow(modifier: Modifier = Modifier, challenge: Challenge) {
    Row(
        modifier = modifier
            .background(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .background(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                .wrapContentSize()
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            imageVector = Icons.Default.Face,
            contentDescription = "Challenge Icon"
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1F)
        ) {
            Text(text = challenge.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Test challenge description", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
private fun ChallengeRowPreview() {
    val challenge = Challenge(
        "Sample Challenge",
        Route.June
    )

    DevCampusUIChallengesTheme {
        ChallengeRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            challenge = challenge
        )
    }
}