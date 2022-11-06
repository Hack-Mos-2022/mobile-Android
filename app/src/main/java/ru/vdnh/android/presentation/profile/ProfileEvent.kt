package ru.vdnh.android.presentation.profile

import ru.vdnh.android.presentation.login.LoginEvent


sealed class ProfileEvent {
    data class PerformLogout(val onClick: () -> Unit) : ProfileEvent()
}