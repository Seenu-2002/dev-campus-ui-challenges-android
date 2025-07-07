package com.seenu.dev.android.devcampusuichallenges.state

import com.seenu.dev.android.devcampusuichallenges.navigation.Route

data class Challenge(
    val name: String,
    val route: Route
)

val challenges = mapOf(
    ChallengeMonth.JUNE_2025 to listOf(
        Challenge(
            name = "Challenge 1", // TODO: String Hardcoding
            route = Route.June
        )
    ),
    ChallengeMonth.JULY_2025 to listOf(
        Challenge(
            name = "Message Card", // TODO: String Hardcoding
            route = Route.July
        )
    )
)