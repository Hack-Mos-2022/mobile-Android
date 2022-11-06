package ru.vdnh.android.presentation.home

import ru.vdnh.android.domain.model.Place

sealed class HomeScreenEvent {
    data class SelectPlace(val place: Place, val onClick: () -> Unit) :
        HomeScreenEvent()
}