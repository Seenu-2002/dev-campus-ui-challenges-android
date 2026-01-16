package com.seenu.dev.android.january26.fresh_start_settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FreshStartSettingsViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<FreshStartSettingsState> = MutableStateFlow(
        FreshStartSettingsState()
    )
    val uiState: StateFlow<FreshStartSettingsState> = _uiState.asStateFlow()

    private var repository: FreshStartSettingsRepository? = null

    fun onIntent(intent: FreshStartSettingsIntent) {
        viewModelScope.launch {
            when (intent) {
                is FreshStartSettingsIntent.FetchInitialData -> {
                    repository = FreshStartSettingsRepository.getInstance(intent.context)
                    repository!!.getDataFlow().collect { state ->
                        _uiState.value = state
                    }
                }

                is FreshStartSettingsIntent.UpdateTheme -> {
                    if (uiState.value.selectedTheme == intent.theme) {
                        return@launch
                    }

                    repository?.updateTheme(intent.theme)
                }

                is FreshStartSettingsIntent.UpdateNotificationsEnabled -> {
                    if (uiState.value.isNotificationsEnabled == intent.isEnabled) {
                        return@launch
                    }

                    repository?.updateNotificationsEnabled(intent.isEnabled)
                }

                is FreshStartSettingsIntent.UpdateDailyStepGoal -> {
                    if (uiState.value.dailyStepGoal == intent.stepGoal) {
                        return@launch
                    }

                    repository?.updateDailyStepGoal(intent.stepGoal)
                }

                is FreshStartSettingsIntent.UpdateMotivationLevel -> {
                    if (uiState.value.motivationLevel == intent.motivationLevel) {
                        return@launch
                    }

                    repository?.updateMotivationLevel(intent.motivationLevel)
                }
            }
        }
    }

}

sealed interface FreshStartSettingsIntent {
    data class FetchInitialData(val context: Context) : FreshStartSettingsIntent
    data class UpdateTheme(val theme: NewYearTheme) : FreshStartSettingsIntent
    data class UpdateNotificationsEnabled(val isEnabled: Boolean) : FreshStartSettingsIntent
    data class UpdateDailyStepGoal(val stepGoal: Int) : FreshStartSettingsIntent
    data class UpdateMotivationLevel(val motivationLevel: Float) : FreshStartSettingsIntent
}