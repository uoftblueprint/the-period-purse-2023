package com.example.theperiodpurse.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.LogPrompt
import com.example.theperiodpurse.data.LogSquare
import com.example.theperiodpurse.data.LogUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LogViewModel(logPrompts: List<LogPrompt>) : ViewModel() {
    private val _uiState = MutableStateFlow(LogUiState(getSquares(logPrompts)))
    val uiState: StateFlow<LogUiState> = _uiState.asStateFlow()

    fun setSquareSelected(logSquare: LogSquare) {
        _uiState.update { currentState ->
            currentState.selectSquares[logSquare.promptTitle] = logSquare.description
            currentState.copy(
                selectSquares =
                    currentState.selectSquares
            )
        }
    }

    fun resetSquareSelected(logSquare: LogSquare) {
        _uiState.update { currentState ->
            currentState.selectSquares[logSquare.promptTitle] = false
            currentState.copy(
                selectSquares =
                currentState.selectSquares
            )
        }
    }

    private fun getSquares(logPrompts: List<LogPrompt>): LinkedHashMap<String, Any> {
        var selectedSquares = LinkedHashMap<String, Any>()
        for (logPrompt in logPrompts) {
            selectedSquares[logPrompt.title] = false
        }
        return(selectedSquares)
    }
}