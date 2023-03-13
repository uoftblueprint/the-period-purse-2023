package com.tpp.theperiodpurse

import com.tpp.theperiodpurse.data.*
import com.tpp.theperiodpurse.ui.calendar.CalendarDayUIState
import com.tpp.theperiodpurse.ui.symptomlog.LogViewModel
import org.junit.Test

class LogViewModelTest {
    private val logSquarePrompts = listOf( // log prompts that use selectable squares
        LogPrompt.Flow
    )
    private val viewModel = LogViewModel(logSquarePrompts)

    @Test
    fun calendarViewModel_UpdateUIState() {
        val flow = FlowSeverity.Light; val cramp = CrampSeverity.Bad;
        val mood = Mood.SAD; val exercise = Exercise.CYCLING
        val exerciseString = "10:01:00"; val sleepString = "11:00:00"
        val state = CalendarDayUIState(
            flow = flow,
            mood = mood,
            exerciseLengthString = exerciseString,
            crampSeverity = cramp,
            exerciseType = exercise,
            sleepString = sleepString
        )
        viewModel.populateWithUIState(state)
        val selectSquares = viewModel.uiState.value.selectSquares
        val promptToText = viewModel.uiState.value.promptToText
        // TODO fix logviewmodel tests
//        assert(selectSquares["Flow"] == flow.displayName)
//        assert(selectSquares["Cramps"] == cramp.displayName)
//        assert(selectSquares["Exercise"] == exercise.displayName)
//        assert(promptToText["Sleep"] == sleepString)
//        assert(promptToText["Exercise"] == exerciseString)
    }
}