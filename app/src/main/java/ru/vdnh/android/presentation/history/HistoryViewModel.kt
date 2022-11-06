package ru.vdnh.android.presentation.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vdnh.android.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _likedPlaces = mutableStateOf(HistoryState())
    val likedPlaces: State<HistoryState> = _likedPlaces

    init {
        viewModelScope.launch {
            userDataRepository.getLikedPlaces().collect {
                _likedPlaces.value = likedPlaces.value.copy(
                    likedPlaceList = it
                )

            }

        }
    }

}