package com.seenu.dev.android.devcampusuichallenges

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seenu.dev.android.devcampusuichallenges.state.ChallengeUiModel
import com.seenu.dev.android.devcampusuichallenges.state.MonthUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LauncherScreen(
    months: List<MonthUiModel>,
    onChallengeClick: (ChallengeUiModel) -> Unit
) {
    val expandedMonth = remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("UI Challenges") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(months) { month ->
                MonthCard(
                    month = month,
                    isExpanded = expandedMonth.value == month.name,
                    onExpandToggle = {
                        expandedMonth.value = if (expandedMonth.value == month.name) null else month.name
                    },
                    onChallengeClick = onChallengeClick
                )
            }
        }
    }
}

@Composable
fun MonthCard(
    month: MonthUiModel,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    onChallengeClick: (ChallengeUiModel) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandToggle() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(month.name, style = MaterialTheme.typography.titleMedium)
                    Text(month.theme, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    month.challenges.forEach { challenge ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onChallengeClick(challenge) }
                                .padding(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Text(challenge.title, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}
