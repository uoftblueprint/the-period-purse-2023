package com.example.theperiodpurse.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.LogSquare
import com.example.theperiodpurse.data.LogUiState
import com.example.theperiodpurse.data.OnboardUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LogViewModel(logSquares: List<LogSquare>) : ViewModel() {
    private val _uiState = MutableStateFlow(LogUiState(getSquares(logSquares)))
    val uiState: StateFlow<LogUiState> = _uiState.asStateFlow()

    fun setSquareSelected(logSquare: LogSquare) {
        _uiState.update { currentState ->
            currentState.selectSquares[logSquare.description] =
                !currentState.selectSquares[logSquare.description]!!
            currentState.copy(
                selectSquares =
                    currentState.selectSquares
            )
        }
    }

    fun isSquareSelected(logSquare: LogSquare) : Boolean {
        return(uiState.value.selectSquares[logSquare.description]?: false)
    }

    private fun getSquares(logSquares: List<LogSquare>): LinkedHashMap<String, Boolean> {
        var selectedSquares = LinkedHashMap<String, Boolean>()
        for (logSquare in logSquares) {
            selectedSquares[logSquare.description] = false
        }
        return(selectedSquares)
    }
}