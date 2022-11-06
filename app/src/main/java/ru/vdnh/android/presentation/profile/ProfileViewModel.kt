package ru.vdnh.android.presentation.profile

import androidx.lifecycle.ViewModel
import ru.vdnh.android.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.PerformLogout -> {
                repository.toggleLoginState()
                event.onClick()
            }
        }
    }
}