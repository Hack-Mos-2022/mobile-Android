package ru.vdnh.android.data.repository

import ru.vdnh.android.domain.model.Advertisement
import ru.vdnh.android.domain.model.FoodItem
import ru.vdnh.android.domain.model.Place
import ru.vdnh.android.domain.repository.HomeRepository

class HomeRepositoryImpl() : HomeRepository {

    override suspend fun getPlaces(): Results<List<Place>> {
        return Results.Success(listOf())
    }

    override suspend fun getAds(): Results<List<Advertisement>> {
        return Results.Success(listOf())
    }

    override suspend fun getFavorites(): Results<List<FoodItem>> {
        return Results.Success(listOf())
    }


}