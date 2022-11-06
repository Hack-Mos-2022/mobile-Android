package ru.vdnh.android.presentation.util

sealed class Screen(val route: String) {
    object Onboarding : Screen(route = "onboarding")
    object LoginScreen : Screen(route = "login_screen")
    object Home : Screen(route = "home_screen")
    object History : Screen(route = "history")
    object Cart : Screen(route = "cart")
    object Profile : Screen(route = "profile")
    object PlaceDetails:Screen(route = "place_details")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
