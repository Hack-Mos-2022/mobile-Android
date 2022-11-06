package ru.vdnh.android.presentation.home

import ru.vdnh.android.domain.model.Advertisement
import ru.vdnh.android.domain.model.FoodItem
import ru.vdnh.android.domain.model.Place

data class HomeScreenState(
    val adsList: List<Advertisement> = emptyList(),
    val foodList: List<FoodItem> = emptyList(),
    val likedPlaceList : List<Place> = emptyList(),
    val placeList : List<Place> = emptyList(),
)
