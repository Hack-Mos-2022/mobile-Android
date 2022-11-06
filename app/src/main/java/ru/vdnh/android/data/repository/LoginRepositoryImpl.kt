package ru.vdnh.android.data.repository

import android.content.Context
import ru.vdnh.android.domain.repository.LoginRepository


class LoginRepositoryImpl(context: Context) : LoginRepository {

    private val prefs = context.getSharedPreferences(LOGIN_STATE, Context.MODE_PRIVATE)
    private val isUserLoggedIn
        get() = prefs.getBoolean(IS_USER_LOGGED_IN, false)
    private val isUserFinishedOnboarding
        get() = prefs.getBoolean(IS_USER_FINISHED_ONBOARDING, false)

    companion object {
        const val LOGIN_STATE = "user_login_state"
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
        const val IS_USER_FINISHED_ONBOARDING = "is_user_finished_onboarding"
    }

    override fun isLoggedIn(): Boolean {
        return true //prefs.getBoolean(IS_USER_LOGGED_IN, false)
    }

    override fun isOnboardingFinished(): Boolean {
        return true //prefs.getBoolean(IS_USER_FINISHED_ONBOARDING, false)
    }

    override fun toggleFinishOnboarding() {
        prefs.edit().putBoolean(IS_USER_FINISHED_ONBOARDING, !isUserFinishedOnboarding).apply()
    }

    override fun toggleLoginState() {
        prefs.edit().putBoolean(IS_USER_LOGGED_IN, !isUserLoggedIn).apply()
    }

}
