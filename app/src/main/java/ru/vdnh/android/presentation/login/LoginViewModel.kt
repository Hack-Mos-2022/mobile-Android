package ru.vdnh.android.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vdnh.android.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _email = mutableStateOf(
        TextFieldState(
            hint = "Enter email or phone number"
        )
    )
    val email: State<TextFieldState> = _email

    private val _password = mutableStateOf(
        TextFieldState(
            hint = "Password"
        )
    )
    val password: State<TextFieldState> = _password

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )

            }
            is LoginEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.PerformLogin -> {
                if (email.value.text == "abcxyz@gmail.com" && password.value.text == "abcdef"){
                    repository.toggleLoginState()
                    event.onClick()
                }
                else {
                    viewModelScope.launch{
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Please enter correct email and password"
                            )
                        )
                    }
                }
            }
        }
    }
}