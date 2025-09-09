package com.seenu.dev.android.devcampusuichallenges.state

import androidx.navigation3.runtime.NavKey

data class MonthUiModel constructor(
    val name: String,
    val theme: String,
    val challenges: List<ChallengeUiModel>
)

data class ChallengeUiModel constructor(
    val title: String,
    val route: NavKey
)