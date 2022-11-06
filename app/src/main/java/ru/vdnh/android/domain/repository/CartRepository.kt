package ru.vdnh.android.domain.repository

import ru.vdnh.android.domain.model.CartItem
import ru.vdnh.android.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun setPlace(place: Place)
    suspend fun getSavedPlace(): Flow<Place>
    suspend fun getCartItems(): Flow<List<CartItem>>
    suspend fun increaseQuantity(cartItem: CartItem)
    suspend fun decreaseQuantity(cartItem: CartItem)
}