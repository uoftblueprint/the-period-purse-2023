package com.tpp.theperiodpurse.ui.setting

import com.tpp.theperiodpurse.data.Symptom

data class SettingsUIState (

    /** Available Symptoms to track*/
    val symptomsOptions: ArrayList<Symptom>,
    val allowReminders: Boolean = false,
)