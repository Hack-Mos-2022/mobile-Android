package ru.vdnh.android.presentation.login

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
}