package ru.vdnh.android.domain.repository

interface LoginRepository {
    fun isLoggedIn(): Boolean
    fun isOnboardingFinished(): Boolean
    fun toggleLoginState()
    fun toggleFinishOnboarding()
}
