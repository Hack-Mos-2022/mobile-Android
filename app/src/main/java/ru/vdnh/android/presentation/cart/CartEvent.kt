package ru.vdnh.android.presentation.cart

import ru.vdnh.android.domain.model.CartItem

sealed class CartEvent{
    data class IncreaseCartQuantity(val cartItem: CartItem): CartEvent()
    data class  DecreaseCartQuantity(val cartItem: CartItem): CartEvent()
}