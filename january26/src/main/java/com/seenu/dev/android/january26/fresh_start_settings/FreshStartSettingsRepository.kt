package com.seenu.dev.android.january26.fresh_start_settings

import android.content.Context
import androidx.annotation.FloatRange
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FreshStartSettingsRepository private constructor(
    private val context: Context
) {

    companion object {
        private val instance: FreshStartSettingsRepository? = null
        fun getInstance(context: Context): FreshStartSettingsRepository {
            return instance ?: FreshStartSettingsRepository(context.applicationContext)
        }
    }

    private val notificationKey = booleanPreferencesKey("notifications_enabled")
    private val themeKey = stringPreferencesKey("selected_theme")
    private val stepGoalKey = intPreferencesKey("daily_step_goal")
    private val motivationLevelKey = floatPreferencesKey("motivation_level")
    private val lastUpdatedAtKey = longPreferencesKey("last_updated_at")

    val Context.datastore: DataStore<Preferences> by preferencesDataStore(
        name = "fresh_start_settings"
    )

    fun getDataFlow(): Flow<FreshStartSettingsState> {
        return with(context) {
            datastore.data.map {
                FreshStartSettingsState(
                    isNotificationsEnabled = it[notificationKey] ?: false,
                    selectedTheme = NewYearTheme.valueOf(
                        it[themeKey] ?: NewYearTheme.COZY_FIREPLACE.name
                    ),
                    dailyStepGoal = it[stepGoalKey] ?: 6000,
                    motivationLevel = it[motivationLevelKey] ?: 0.5F,
                    lastUpdatedAt = it[lastUpdatedAtKey]
                )
            }
        }
    }

    suspend fun updateTheme(theme: NewYearTheme) {
        context.datastore.edit {
            it[themeKey] = theme.name
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }

    suspend fun updateNotificationsEnabled(isEnabled: Boolean) {
        context.datastore.edit {
            it[notificationKey] = isEnabled
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }

    suspend fun updateDailyStepGoal(stepGoal: Int) {
        context.datastore.edit {
            it[stepGoalKey] = stepGoal
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }

    suspend fun updateMotivationLevel(@FloatRange(from = 0.0, to = 1.0) level: Float) {
        context.datastore.edit {
            it[motivationLevelKey] = level
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }

    suspend fun clearSettings() {
        context.datastore.edit {
            it.clear()
        }
    }

}

data class FreshStartSettingsState(
    val isNotificationsEnabled: Boolean = false,
    val selectedTheme: NewYearTheme = NewYearTheme.COZY_FIREPLACE,
    val dailyStepGoal: Int = 6000,
    val motivationLevel: Float = 0.5F,
    val lastUpdatedAt: Long? = null,
)

enum class NewYearTheme constructor(val label: String) {
    COZY_FIREPLACE("Cozy Fireplace"),
    SNOWY_MOUNTAINS("Snowy Mountains"),
    CITY_LIGHTS("City Lights"),
}
