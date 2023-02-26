package com.example.theperiodpurse

import com.example.theperiodpurse.data.Date
import com.example.theperiodpurse.data.Symptom

// data class
data class AppUiState (
    // Preffered Symptoms to be tracked
    val trackedSymptoms: List<Symptom> = listOf(),
    val dates: List<Date> = emptyList(),
)