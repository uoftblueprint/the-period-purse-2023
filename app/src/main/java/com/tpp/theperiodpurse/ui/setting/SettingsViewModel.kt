package com.tpp.theperiodpurse.ui.setting

import androidx.lifecycle.ViewModel
import com.tpp.theperiodpurse.data.Symptom
import com.tpp.theperiodpurse.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SettingsViewModel @Inject constructor (
    userRepository: UserRepository) : ViewModel()  {

    private val _uiState = MutableStateFlow(SettingsUIState(userRepository.getUser(1).symptomsToTrack))
    val uiState: StateFlow<SettingsUIState> = _uiState.asStateFlow()
    val symptoms = _uiState.value.symptomsOptions


    fun updateSymptoms(symptom: Symptom): Boolean {
        if(symptoms.contains(symptom)){
            symptoms.remove(symptom)
            _uiState.update { currentState -> currentState.copy(symptomsOptions = symptoms)}
            return false
        } else{
            symptoms.add(symptom)
            _uiState.update { currentState -> currentState.copy(symptomsOptions = symptoms)}
            return true
        }

    }

    fun isSymptomChecked(symptom: Symptom): Boolean{
        return symptoms.contains(symptom)
    }

    fun toggleallowReminders(){
        val currentReminderState = _uiState.value.allowReminders
        _uiState.update { currentState -> currentState.copy(allowReminders = !currentReminderState)}
    }

}