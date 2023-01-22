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
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "symptoms_to_track")
    val symptomsToTrack: ArrayList<Symptom> = ArrayList(),

    @Column(nullable = false)
    val periodHistory: ArrayList<Date> = ArrayList(),

    @Column(name = "average_period_length")
    var averagePeriodLength: Int = 0,

    @Column(name = "average_cycle_length")
    var averageCycleLength: Int = 0,

    @Column(name = "days_since_last_period")
    var daysSinceLastPeriod: Int = 0
    )