package com.example.theperiodpurse.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class User(
    val symptomsToTrack: ArrayList<Symptom> = ArrayList(),
    val periodHistory: ArrayList<Date> = ArrayList(),
    val averagePeriodLength: Int = 0,
    val averageCycleLength: Int = 0,
    val daysSinceLastPeriod: Int = 0
    )

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private val TAG: String = "UserPreferencesRepo"

    private object PreferencesKeys {
        val SYMPTOMS_TO_TRACK = stringPreferencesKey("symptoms_to_track")
        val PERIOD_HISTORY = stringPreferencesKey("period_history")
        val AVERAGE_PERIOD_LENGTH = intPreferencesKey("average_period_length")
        val AVERAGE_CYCLE_LENGTH = intPreferencesKey("average_cycle_length")
        val DAYS_SINCE_LAST_PERIOD = intPreferencesKey("days_since_last_period")
    }

    suspend fun updateSymptomsToTrack(symptomsToTrack: ArrayList<Symptom>) {
        val json = Gson().toJson(symptomsToTrack)
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SYMPTOMS_TO_TRACK] = json
        }
    }

    suspend fun updatePeriodHistory(periodHistory: ArrayList<Date>) {
        val json = Gson().toJson(periodHistory)
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PERIOD_HISTORY] = json
        }
    }

    suspend fun updateAveragePeriodLength(averagePeriodLength: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AVERAGE_PERIOD_LENGTH] = averagePeriodLength
        }
    }

    suspend fun updateAverageCycleLength(averageCycleLength: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AVERAGE_CYCLE_LENGTH] = averageCycleLength
        }
    }

    suspend fun updateDaysSinceLastPeriod(daysSinceLastPeriod: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DAYS_SINCE_LAST_PERIOD] = daysSinceLastPeriod
        }
    }

    private fun mapUser(preferences: Preferences): User {
        val symptomsToTrackTypeToken = object: TypeToken<ArrayList<Symptom>>() {}.type
        val symptomsToTrackJson = preferences[PreferencesKeys.SYMPTOMS_TO_TRACK] ?: ""
        val symptomsToTrack = Gson().fromJson<ArrayList<Symptom>>(symptomsToTrackJson,
            symptomsToTrackTypeToken)

        val periodHistoryTypeToken = object: TypeToken<ArrayList<Date>>() {}.type
        val periodHistoryJson = preferences[PreferencesKeys.PERIOD_HISTORY] ?: ""
        val periodHistory = Gson().fromJson<ArrayList<Date>>(periodHistoryJson,
            periodHistoryTypeToken)

        val averagePeriodLength = preferences[PreferencesKeys.AVERAGE_PERIOD_LENGTH] ?: 0

        val averageCycleLength = preferences[PreferencesKeys.AVERAGE_CYCLE_LENGTH] ?: 0

        val daysSinceLastPeriod = preferences[PreferencesKeys.DAYS_SINCE_LAST_PERIOD] ?: 0

        return User(symptomsToTrack, periodHistory, averagePeriodLength, averageCycleLength,
            daysSinceLastPeriod)
    }
}