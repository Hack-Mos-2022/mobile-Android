package ru.vdnh.android.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vdnh.android.data.repository.Results
import ru.vdnh.android.domain.repository.CartRepository
import ru.vdnh.android.domain.repository.HomeRepository
import ru.vdnh.android.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val userDataRepository: UserDataRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _homeScreenState = mutableStateOf(
        HomeScreenState()
    )
    val homeScreenState: State<HomeScreenState> = _homeScreenState

    init {
        viewModelScope.launch {
            when (val result = repository.getAds()) {
                is Results.Success -> _homeScreenState.value = homeScreenState.value.copy(
                    adsList = result.data
                )
                is Results.Error -> {
                }
            }

            when (val result = repository.getFavorites()) {
                is Results.Success -> _homeScreenState.value = homeScreenState.value.copy(
                    foodList = result.data
                )
                is Results.Error -> {

                }
            }

            when (val result = repository.getPlaces()) {
                is Results.Success -> _homeScreenState.value = homeScreenState.value.copy(
                    placeList = result.data,
                )
                is Results.Error -> {

                }
            }

            userDataRepository.getLikedPlaces().collect {
                _homeScreenState.value = homeScreenState.value.copy(
                    likedPlaceList = it
                )
            }
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.SelectPlace -> {
                viewModelScope.launch {
                    cartRepository.setPlace(event.place)
                    event.onClick()
                }
            }
        }
    }


}