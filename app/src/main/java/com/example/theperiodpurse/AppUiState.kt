package com.example.theperiodpurse

import com.example.theperiodpurse.data.Symptom

// data class
data class AppUiState (
    // Preffered Symptoms to be tracked
    val trackedSymptoms: List<Symptom> = listOf()
)