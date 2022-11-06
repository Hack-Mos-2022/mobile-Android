package ru.vdnh.android.presentation.history

import ru.vdnh.android.domain.model.Place

data class HistoryState(
    val likedPlaceList: List<Place> = emptyList(),
)
