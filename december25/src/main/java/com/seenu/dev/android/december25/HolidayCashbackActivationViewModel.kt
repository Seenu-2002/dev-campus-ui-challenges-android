package com.seenu.dev.android.december25

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HolidayCashbackActivationViewModel : ViewModel() {

    private val _cardState: MutableStateFlow<HolidayCashbackActivationUiState> = MutableStateFlow(
        HolidayCashbackActivationUiState()
    )
    val cardState: StateFlow<HolidayCashbackActivationUiState> = _cardState.asStateFlow()

    fun onIntent(intent: HolidayCashbackActivationIntent) {
        viewModelScope.launch {
            when (intent) {
                is HolidayCashbackActivationIntent.OnCardNumberChange -> {
                    val state = _cardState.value
                    val type = when {
                        intent.cardNumber.startsWith("4") -> {
                            CardType.VISA
                        }
                        else -> {
                            CardType.MASTERCARD
                        }
                    }
                    val cardNumber = if (intent.cardNumber.length > 16) {
                        intent.cardNumber.take(16)
                    } else {
                        intent.cardNumber
                    }
                    _cardState.value = state.copy(
                        cardNumber = intent.cardNumber,
                        cardType = type,
                        cardStatus = CardStatus.IDLE,
                    )
                }

                HolidayCashbackActivationIntent.OnActivateCardClick -> {
                    val state = _cardState.value
                    _cardState.value = state.copy(
                        cardStatus = CardStatus.ACTIVATING,
                    )
                    if (validateCardNumber(state.cardNumber)) {
                        _cardState.value = state.copy(
                            cardStatus = CardStatus.ACTIVATED,
                        )
                    } else {
                        _cardState.value = state.copy(
                            cardStatus = CardStatus.ACTIVATION_ERROR,
                        )
                    }
                }

                HolidayCashbackActivationIntent.OnActionDone -> {
                    val state = _cardState.value
                    _cardState.value = state.copy(
                        cardNumber = "",
                        cardType = CardType.MASTERCARD,
                        cardStatus = CardStatus.IDLE,
                    )
                }
            }
        }
    }

    private suspend fun validateCardNumber(cardNumber: String): Boolean {
        delay(1_000)
        if (cardNumber.length != 16) {
            return false
        }

        var sum = 0
        for (index in cardNumber.lastIndex downTo 0) {
            var n = cardNumber[index].digitToInt()
            if (index % 2 == 0) {
                n *= 2
                if (n > 9) {
                    n -= 9
                }
            }
            sum += n
        }

        return sum % 10 == 0
    }

}

sealed interface HolidayCashbackActivationIntent {
    data class OnCardNumberChange(val cardNumber: String) : HolidayCashbackActivationIntent
    data object OnActivateCardClick : HolidayCashbackActivationIntent

    data object OnActionDone : HolidayCashbackActivationIntent
}

data class HolidayCashbackActivationUiState(
    val cardNumber: String = "",
    val cardType: CardType = CardType.MASTERCARD,
    val cardStatus: CardStatus = CardStatus.IDLE
)

enum class CardType {
    VISA, MASTERCARD
}

enum class CardStatus {
    IDLE, ACTIVATED, ACTIVATING, ACTIVATION_ERROR
}