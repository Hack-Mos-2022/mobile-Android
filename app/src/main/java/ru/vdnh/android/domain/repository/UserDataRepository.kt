package ru.vdnh.android.domain.repository

import ru.vdnh.android.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun updateLikedPlace(place: Set<String>)
    suspend fun getLikedPlaces(): Flow<List<Place>>

}