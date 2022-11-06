package ru.vdnh.android.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vdnh.android.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartState = mutableStateOf(
        CartState()
    )

    val cartState: State<CartState> = _cartState

    init {
        viewModelScope.launch {
            cartRepository.getSavedPlace().collect { place ->
                cartRepository.getCartItems().collect {
                    _cartState.value = cartState.value.copy(
                        place = place,
                        list = it.toMutableList()
                    )
                }
            }

        }
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.IncreaseCartQuantity -> {
                viewModelScope.launch {
                    cartRepository.increaseQuantity(event.cartItem)
                }
            }
            is CartEvent.DecreaseCartQuantity -> {
                viewModelScope.launch {
                    cartRepository.decreaseQuantity(event.cartItem)
                }
            }

        }
    }


}