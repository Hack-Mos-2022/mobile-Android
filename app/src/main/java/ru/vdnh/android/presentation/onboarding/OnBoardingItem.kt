package ru.vdnh.android.presentation.onboarding

class OnBoardingItem(
    val title: Int,
    val text: Int,
    val image: Int
) {
    companion object {
        fun get(): List<OnBoardingItem> {
            return listOf(
            )
        }
    }
}