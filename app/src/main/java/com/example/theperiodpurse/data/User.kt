package com.example.theperiodpurse.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    @TypeConverters(SymptomConverter::class)
    val symptomsToTrack: ArrayList<Symptom> = ArrayList(),
    @TypeConverters(DateConverter::class)
    val periodHistory: ArrayList<Date> = ArrayList(),
    val averagePeriodLength: Int = 0,
    val averageCycleLength: Int = 0,
    val daysSinceLastPeriod: Int = 0
    )
