package com.seenu.dev.android.devcampusuichallenges.state

import androidx.compose.runtime.Composable
import com.seenu.dev.android.devcampusuichallenges.navigation.Route


val months
    get() = listOf(
        MonthUiModel(
            "September 2025",
            "Designing the Festival",
            septemberChallenges
        )
    )

private val septemberChallenges
    get() = listOf(
        ChallengeUiModel(
            "Expandable Lineup List",
            Route.September1
        ),
        ChallengeUiModel(
            "Ticket Builder",
            Route.September2
        ),
        ChallengeUiModel(
            "Festival Map",
            Route.September3
        ),
        ChallengeUiModel(
            "Accessible Audio Schedule",
            Route.September4
        ),
        ChallengeUiModel(
            "Multi Stage Timeline",
            Route.September5
        ),
    )