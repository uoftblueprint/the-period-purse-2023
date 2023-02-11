package com.example.theperiodpurse

import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.DateRepository
import com.example.theperiodpurse.data.Symptom
import com.example.theperiodpurse.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor (private val userRepository: UserRepository,
                                        private val dateRepository: DateRepository
                                        ): ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun loadData() {
        val exc = Symptom from R.string.exercise
        val cram = Symptom from R.string.cramps

        val symptoms: MutableList<Symptom> = mutableListOf()

        for (symptom in listOf(exc, cram)) {
            if (symptom != null) {
                symptoms.add(symptom)
            }
        }

        _uiState.update { currentState ->
            currentState.copy(trackedSymptoms = symptoms)
        }
    }
}