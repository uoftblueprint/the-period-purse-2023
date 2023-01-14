package com.example.theperiodpurse.data
import java.util.Date

data class Date (
    val date: Date,
    val flow: FlowSeverity,
    val mood: Mood,
    val exerciseLength: Date,
    val exerciseType: Exercise,
    val crampSeverity: CrampSeverity,
    val sleep: Date
    )