package ru.vdnh.android.domain.repository

import ru.vdnh.android.data.repository.Results
import ru.vdnh.android.domain.model.Advertisement
import ru.vdnh.android.domain.model.FoodItem
import ru.vdnh.android.domain.model.Place

interface HomeRepository {

    suspend fun getPlaces() : Results<List<Place>>
    suspend fun getAds(): Results<List<Advertisement>>
    suspend fun getFavorites():Results<List<FoodItem>>

}