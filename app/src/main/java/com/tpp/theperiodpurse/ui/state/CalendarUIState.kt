package com.tpp.theperiodpurse.ui.state

import com.tpp.theperiodpurse.data.model.*
import java.time.LocalDate
import kotlin.collections.LinkedHashMap

data class CalendarUIState(
    var days: LinkedHashMap<LocalDate, CalendarDayUIState>,
    var selectedSymptom: Symptom,
)
