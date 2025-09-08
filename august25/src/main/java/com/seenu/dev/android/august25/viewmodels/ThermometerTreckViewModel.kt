package com.seenu.dev.android.august25.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThermometerTreckViewModel : ViewModel() {

    private val _isTrackingStarted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTrackingStarted: StateFlow<Boolean> = _isTrackingStarted.asStateFlow()

    private val _temperatures: MutableStateFlow<List<Double>> = MutableStateFlow(listOf())
    val temperatures: StateFlow<List<Double>> = _temperatures.asStateFlow()


    val maxReadingCount: Int = 20

    fun startTracking() {
        viewModelScope.launch {
            _isTrackingStarted.value = true
            startEmittingValues()
        }
    }

    private fun startEmittingValues() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                flowOf(
                    -100.0,
                    0.0,
                    -30.5,
                    10.0,
                    20.0,
                    -60.0,
                    35.5,
                    -51.3,
                    22.0,
                    19.8,
                    45.0,
                    55.1,
                    60.2,
                    90.0,
                    -49.9,
                    5.0,
                    12.3,
                    8.8,
                    0.5,
                    -2.0,
                    30.0,
                    35.0,
                    27.5,
                    18.1,
                    15.6,
                    11.0,
                    17.3,
                    33.8,
                    41.2,
                    -80.0
                )
                    .filter {
                        it >= -50
                    }
                    .onEach {
                        delay(250L)
                    }
                    .take(20)
                    .collect { temperature ->
                        _temperatures.value = _temperatures.value + temperature.toFahrenheit()
                    }
            }
        }
    }

    private fun Double.toFahrenheit(): Double {
        return (this * 9 / 5) + 32
    }

    fun reset() {
        viewModelScope.launch {
            _isTrackingStarted.value = false
            _temperatures.value = listOf()
        }
    }

}