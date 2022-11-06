package ru.vdnh.android.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppElevations(
    val card: Dp = 4.dp,
//    val paddingMedium: Dp = 8.dp,
//    val paddingLarge: Dp = 24.dp
)

internal val LocalElevations = staticCompositionLocalOf { AppElevations() }