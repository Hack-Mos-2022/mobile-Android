package ru.vdnh.android.presentation.details

import ru.vdnh.android.domain.model.CartItem

sealed class DetailScreenEvent {
    data class SetPlace(val place: String) : DetailScreenEvent()
    object ToggleRecommendedSectionExpandedState : DetailScreenEvent()
    object ToggleLikedStatus : DetailScreenEvent()
    data class IncreaseCartQuantity(val cartItem: CartItem): DetailScreenEvent()
    data class  DecreaseCartQuantity(val cartItem: CartItem): DetailScreenEvent()



}