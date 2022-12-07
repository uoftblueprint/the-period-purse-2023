package com.example.theperiodpurse.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.LogSquare
import com.example.theperiodpurse.data.LogUiState
import com.example.theperiodpurse.data.OnboardUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LogViewModel(logSquares: List<LogSquare>) : ViewModel() {
    private val _uiState = MutableStateFlow(LogUiState(getSquares(logSquares)))
    val uiState: StateFlow<LogUiState> = _uiState.asStateFlow()

    private fun getSquares(logSquares: List<LogSquare>): Map<String, Boolean> {
        var selectedSquares = LinkedHashMap<String, Boolean>()
        for (logSquare in logSquares) {
            selectedSquares[logSquare.description] = false
        }
        return(selectedSquares)
    }
}