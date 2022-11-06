package ru.vdnh.android.presentation.details

import ru.vdnh.android.domain.model.CartItem
import ru.vdnh.android.domain.model.Place

data class DetailScreenState(
    val place: Place? = null,
    val recommendedExpandedState: Boolean = true,
    val isLiked: Boolean = false,
    val menuList: List<CartItem> = emptyList(),
    val recommendedList: List<CartItem> = emptyList()
)
