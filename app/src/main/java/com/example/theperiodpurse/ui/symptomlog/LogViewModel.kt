package com.example.theperiodpurse.ui.symptomlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.theperiodpurse.AppViewModel
import com.example.theperiodpurse.data.LogPrompt
import com.example.theperiodpurse.data.LogSquare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LogViewModel(logPrompts: List<LogPrompt>, appViewModel: AppViewModel) : ViewModel() {
    private val _uiState = MutableStateFlow(LogUiState(
        getSquares(logPrompts),
        getText(logPrompts)
    ))
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

    fun getSquareSelected(logPrompt: LogPrompt) : String? {
        if (uiState.value.selectSquares[logPrompt.title] !is String) {
            return(null)
        } else {
            return(uiState.value.selectSquares[logPrompt.title] as String)
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

    fun setText(logPrompt: LogPrompt, newText: String) {
        _uiState.update { currentState ->
            currentState.promptToText[logPrompt.title] = newText
            currentState.copy(
                selectSquares =
                currentState.selectSquares
            )
        }
    }

    fun getText(logPrompt: LogPrompt): String{
        return(uiState.value.promptToText[logPrompt.title]?: "")
    }

    fun resetText(logPrompt: LogPrompt) {
        _uiState.update { currentState ->
            currentState.promptToText[logPrompt.title] = ""
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

    private fun getText(logPrompts: List<LogPrompt>): LinkedHashMap<String, String> {
        var promptToText = LinkedHashMap<String, String>()
        for (logPrompt in logPrompts) {
            promptToText[logPrompt.title] = ""
        }
        return(promptToText)
    }
}