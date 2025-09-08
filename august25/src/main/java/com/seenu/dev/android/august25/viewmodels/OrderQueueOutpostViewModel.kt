package com.seenu.dev.android.august25.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class OrderQueueOutpostViewModel : ViewModel() {

    private val _isStarted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isStarted: StateFlow<Boolean> = _isStarted.asStateFlow()

    private val _orderCollectionStatus: MutableStateFlow<OrderCollectionStatus> = MutableStateFlow(
        OrderCollectionStatus.HAVE_NOT_STARTED
    )
    val orderCollectionStatus: StateFlow<OrderCollectionStatus> =
        _orderCollectionStatus.asStateFlow()

    private var order = AtomicInteger(0)

    private val _pendingOrderCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val pendingOrderCount: StateFlow<Int> = _pendingOrderCount.asStateFlow()

    private var sendJob: Job? = null
    private var receiveJob: Job? = null

    fun startProcessingOrders() {
        viewModelScope.launch {
            _isStarted.value = true
            sendOrders()
        }
    }

    fun startCollecting() {
        resumeCollecting()
    }

    fun resumeCollecting() {
        viewModelScope.launch {
            _orderCollectionStatus.value = OrderCollectionStatus.COLLECTING
            receiveJob = viewModelScope.launch {
                while (true) {
                    if (order.get() > 0) {
                        Log.d(
                            "OrderQueueOutpostVM",
                            "Collecting order ID -> ${order.getAndDecrement()}"
                        )
                        _pendingOrderCount.value = order.get()
                    }
                    delay((100..250).random().toLong())
                }
            }
        }
    }

    fun pauseCollecting() {
        viewModelScope.launch {
            _orderCollectionStatus.value = OrderCollectionStatus.PAUSED
            receiveJob?.cancel()
            receiveJob = null
        }
    }

    private fun sendOrders() {
        sendJob = viewModelScope.launch {
            while (true) {
                _pendingOrderCount.value = order.addAndGet(1)
                delay(250L)
            }
        }
    }

}

enum class OrderCollectionStatus {
    PAUSED, COLLECTING, HAVE_NOT_STARTED
}