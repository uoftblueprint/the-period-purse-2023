package com.tpp.theperiodpurse

import com.tpp.theperiodpurse.data.CrampSeverity
import com.tpp.theperiodpurse.data.Exercise
import com.tpp.theperiodpurse.data.FlowSeverity
import com.tpp.theperiodpurse.data.Mood
import com.tpp.theperiodpurse.ui.calendar.CalendarDayUIState
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import org.junit.Test
import java.time.LocalDate

class CalendarViewModelTest {
    private val viewModel = CalendarViewModel()
    private val originalDayUIState = CalendarDayUIState(FlowSeverity.Heavy, Mood.ANGRY,
        "11:00:00", Exercise.CARDIO, CrampSeverity.Bad, "08:00:00")
    private val date = LocalDate.of(2020, 1, 1)

    @Test
    fun calendarViewModel_SetDayInfo() {
        assert(!viewModel.uiState.value.days.containsKey(date));
        viewModel.setDayInfo(date, originalDayUIState);
        assert(viewModel.uiState.value.days[date] == originalDayUIState);
    }
    @Test
    fun calendarViewModel_UpdateDayInfo() {
        viewModel.setDayInfo(date, originalDayUIState);
        assert(viewModel.uiState.value.days[date]?.flow == originalDayUIState.flow);
        val newDayUIState = CalendarDayUIState(FlowSeverity.None)
        viewModel.updateDayInfo(date, newDayUIState)
        assert(viewModel.uiState.value.days[date]?.flow == FlowSeverity.None);
    }
}