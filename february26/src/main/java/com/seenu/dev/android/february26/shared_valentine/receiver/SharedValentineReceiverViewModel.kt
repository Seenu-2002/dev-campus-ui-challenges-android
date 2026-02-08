package com.seenu.dev.android.february26.shared_valentine.receiver

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seenu.dev.android.february26.shared_valentine.components.ValentineGift
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedValentineReceiverViewModel : ViewModel() {

    companion object {
        private val PREF_KEY = "shared_valentine_receiver_prefs"
    }

    private val _uiState: MutableStateFlow<SharedValentineReceiverUiState> = MutableStateFlow(
        SharedValentineReceiverUiState()
    )
    val uiState: StateFlow<SharedValentineReceiverUiState> = _uiState.asStateFlow()

    private var sharedPref: SharedPreferences? = null

    fun getReceivedItems(context: Context) {
        sharedPref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val receivedItems = sharedPref?.getStringSet("received_items", emptySet()) ?: emptySet()
        _uiState.update {
            it.copy(receivedItems = receivedItems.mapNotNull { name ->
                try {
                    ValentineGift.valueOf(name)
                } catch (e: IllegalArgumentException) {
                    null
                }
            }.toSet())
        }
    }

    fun saveReceivedValentine(valentineGift: ValentineGift) {
        viewModelScope.launch {
            val currentItems = _uiState.value.receivedItems.toMutableSet()
            currentItems.add(valentineGift)
            _uiState.update { it.copy(receivedItems = currentItems) }
            sharedPref?.edit()?.putStringSet("received_items", currentItems.map { it.name }.toSet())
                ?.apply()
        }
    }

}

data class SharedValentineReceiverUiState(
    val receivedItems: Set<ValentineGift> = emptySet()
)