package com.tpp.theperiodpurse

import com.tpp.theperiodpurse.data.Date
import com.tpp.theperiodpurse.data.Symptom

// data class
data class AppUiState (
    // Preffered Symptoms to be tracked
    var trackedSymptoms: List<Symptom> = listOf(),
    var allowReminders: Boolean = false,
    var reminderFrequency: String = "Every day",
    var reminderTime: String = "12:00 PM",
    var dates: List<Date> = emptyList(),
)