package com.tpp.theperiodpurse.ui.setting

data class SettingsUIState (

    /** Available Symptoms dates for the order*/
    val symptomsOptions: List<String> = listOf(),
    val allowReminders: Boolean = false,
)