package ru.vdnh.android.data.repository

import ru.vdnh.android.domain.model.CartItem
import ru.vdnh.android.domain.model.Place
import ru.vdnh.android.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class CartRepositoryImpl : CartRepository {

    private val cartList = MutableStateFlow<MutableSet<CartItem>>(mutableSetOf())
    private var currentPlace: Place? = null

    override suspend fun setPlace(place: Place) {
        currentPlace = place
        place.events.forEach {
            cartList.value.add(CartItem(it, 0))
        }
    }

    override suspend fun getSavedPlace(): Flow<Place> = flow {
        if (currentPlace != null) {
            emit(currentPlace!!)
        }
    }

    override suspend fun getCartItems(): Flow<List<CartItem>> = flow {
        emit(cartList.value.toMutableList())
    }

    override suspend fun increaseQuantity(cartItem: CartItem) {
        cartList.value.map {
            if (it == cartItem) {
                it.noOfItems++
            }
        }

    }

    override suspend fun decreaseQuantity(cartItem: CartItem) {
        if (cartItem.noOfItems > 0) {
            cartList.value.map {
                if (it == cartItem) {
                    it.noOfItems--
                }
            }
        }
    }
}