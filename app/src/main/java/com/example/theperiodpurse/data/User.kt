package com.example.theperiodpurse.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val symptomsToTrack: Symptom,
//    val periodHistory: Date,
    val averagePeriodLength: Int = 0,
    val averageCycleLength: Int = 0,
    val daysSinceLastPeriod: Int = 0
    )
