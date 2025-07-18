package com.seenu.dev.android.devcampusuichallenges.state

import com.seenu.dev.android.devcampusuichallenges.navigation.Route

data class Challenge(
    val name: String,
    val route: Route
)

val challenges = mapOf(
    ChallengeMonth.JUNE_2025 to listOf(
        Challenge(
            name = "Challenge 1",
            route = Route.June
        )
    ),
    ChallengeMonth.JULY_2025 to listOf(
        Challenge(
            name = "Message Card",
            route = Route.July
        ),
        Challenge(
            name = "Bottom Navigation with Unread badges",
            route = Route.July2
        ),
        Challenge(
            name = "Emoji Reaction Bubble",
            route = Route.July3
        )
    )
)