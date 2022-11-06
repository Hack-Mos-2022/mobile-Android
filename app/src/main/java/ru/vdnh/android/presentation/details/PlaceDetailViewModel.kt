package ru.vdnh.android.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vdnh.android.domain.repository.CartRepository
import ru.vdnh.android.domain.repository.HomeRepository
import ru.vdnh.android.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val userDataRepository: UserDataRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _detailScreenState = mutableStateOf(DetailScreenState())
    val detailScreenState: State<DetailScreenState> = _detailScreenState

    init {
        viewModelScope.launch {

            cartRepository.getCartItems().collect {
                _detailScreenState.value = detailScreenState.value.copy(
                    menuList = it,
                    recommendedList = it.shuffled()
                    .dropLast(it.size - 5)
                )
            }

            cartRepository.getSavedPlace().collect { place ->
                _detailScreenState.value = detailScreenState.value.copy(
                    place = place,
                )
                userDataRepository.getLikedPlaces().collect { placeList ->
                    _detailScreenState.value = detailScreenState.value.copy(
                        isLiked = false
                    )
                }

            }
        }
    }


    fun onEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.ToggleRecommendedSectionExpandedState -> {
                _detailScreenState.value = detailScreenState.value.copy(
                    recommendedExpandedState = !detailScreenState.value.recommendedExpandedState
                )
            }
            is DetailScreenEvent.ToggleLikedStatus -> {
                if (detailScreenState.value.isLiked) {
                    viewModelScope.launch {
                        userDataRepository.getLikedPlaces().collect {
                            val likedList = it.toMutableList()
                            likedList.remove(detailScreenState.value.place!!)
                            userDataRepository.updateLikedPlace(likedList.map { place -> place.name }
                                .toSet())
                        }
                    }
                } else {

                    viewModelScope.launch {
                        userDataRepository.getLikedPlaces().collect {
                            val likedList = it.toMutableList()
                            likedList.add(detailScreenState.value.place!!)
                            userDataRepository.updateLikedPlace(likedList.map { place -> place.name }
                                .toSet())
                        }
                    }
                }
            }
            is DetailScreenEvent.IncreaseCartQuantity -> {
                viewModelScope.launch {
                    cartRepository.increaseQuantity(event.cartItem)
                }
            }
            is DetailScreenEvent.DecreaseCartQuantity -> {
                viewModelScope.launch {
                    cartRepository.decreaseQuantity(event.cartItem)
                }
            }

            is DetailScreenEvent.SetPlace -> {
                //TODO()
            }
        }
    }

}


