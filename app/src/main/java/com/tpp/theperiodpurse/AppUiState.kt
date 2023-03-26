package com.tpp.theperiodpurse

import com.tpp.theperiodpurse.data.Date
import com.tpp.theperiodpurse.data.Symptom

// data class
data class AppUiState (
    // Preffered Symptoms to be tracked
    val trackedSymptoms: List<Symptom> = listOf(),
    val allowReminders: Boolean = false,
    val dates: List<Date> = emptyList(),
)