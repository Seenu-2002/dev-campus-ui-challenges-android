package com.seenu.dev.android.devcampusuichallenges.state

import androidx.navigation3.runtime.NavKey

data class MonthUiModel(
    val name: String,
    val theme: String,
    val challenges: List<ChallengeUiModel>
)

data class ChallengeUiModel(
    val title: String,
    val route: NavKey
)