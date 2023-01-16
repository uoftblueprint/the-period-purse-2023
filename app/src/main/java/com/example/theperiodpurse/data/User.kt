package com.example.theperiodpurse.data

data class User(
    val symptomsToTrack: ArrayList<Symptom> = ArrayList(),
    val periodHistory: ArrayList<Date> = ArrayList(),
    val averagePeriodLength: Int = 0,
    val averageCycleLength: Int = 0,
    val daysSinceLastPeriod: Int = 0
    )
