package com.tpp.theperiodpurse.ui.state

import com.tpp.theperiodpurse.data.model.Symptom

data class SettingsUIState(

    /** Available Symptoms to track*/
    val symptomsOptions: List<Symptom>,
    val allowReminders: Boolean = false,
    var darkMode: Boolean = false,
)
