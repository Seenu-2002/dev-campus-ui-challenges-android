package com.seenu.dev.android.devcampusuichallenges.state

import androidx.compose.runtime.Composable
import com.seenu.dev.android.devcampusuichallenges.navigation.Route


val months
    get() = listOf(
        MonthUiModel(
            "September 2025",
            "Designing the Festival",
            septemberChallenges
        ),
        MonthUiModel(
            "October 2025",
            "Android Halloween Lab",
            octoberChallenges
        ),
    )

private val octoberChallenges
    get() = listOf(
        ChallengeUiModel(
            "Haunted Theme Switcher",
            Route.October2
        ),
        ChallengeUiModel(
            "Cursed Countdown",
            Route.October3
        ),
        ChallengeUiModel(
            "Coven Booking Desk",
            Route.October4
        ),
        ChallengeUiModel(
            "Halloween Skeleton Puzzle",
            Route.October5
        ),
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