package com.tpp.theperiodpurse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpp.theperiodpurse.data.Date
import com.tpp.theperiodpurse.data.DateRepository
import com.tpp.theperiodpurse.data.Symptom
import com.tpp.theperiodpurse.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor (private val userRepository: UserRepository,
                                        private val dateRepository: DateRepository
                                        ): ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun loadData() {
        val trackedSymptoms: MutableList<Symptom> = mutableListOf()
        viewModelScope.launch {
            Log.d(
                "Lookies Here",
                withContext(Dispatchers.Main) { !userRepository.isEmpty() }.toString()
            )
            if (withContext(Dispatchers.Main) { !userRepository.isEmpty() }) {
                val user = withContext(Dispatchers.Main) { userRepository.getUser(1) }
                trackedSymptoms.add(Symptom.FLOW)
                trackedSymptoms.addAll(user.symptomsToTrack)
                _uiState.update { currentState ->
                    currentState.copy(trackedSymptoms = trackedSymptoms)
                }
            }
            withContext(Dispatchers.IO) {
                dateRepository.getAllDates().collect { dates ->
                    _uiState.value = _uiState.value.copy(dates = dates)
                }
            }
        }
    }

    fun saveDate(date: Date) {
        dateRepository.addDate(date)
    }
}