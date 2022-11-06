package ru.vdnh.android.presentation.cart

import ru.vdnh.android.domain.model.CartItem
import ru.vdnh.android.domain.model.Place

data class CartState(
    val place: Place? = null,
    val list: MutableList<CartItem> = mutableListOf()
)


