package ru.vdnh.android.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ru.vdnh.android.domain.model.Place
import ru.vdnh.android.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding_pref")

class UserDataRepositoryImpl(context: Context) : UserDataRepository {

    private object PreferencesKey {
        val likedPlaces = stringSetPreferencesKey(name = "liked_places")
    }


    private val dataStore = context.dataStore

    override suspend fun updateLikedPlace(place: Set<String>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.likedPlaces] = place
        }
    }

    override suspend fun getLikedPlaces(): Flow<List<Place>> {
         val list = mutableListOf<Place>()
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.likedPlaces] ?: setOf()
                onBoardingState.forEach { name->
                    // todo mock data
                }
                list
            }
    }

}