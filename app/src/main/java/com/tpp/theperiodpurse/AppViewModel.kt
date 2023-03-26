package com.tpp.theperiodpurse

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpp.theperiodpurse.data.Date
import com.tpp.theperiodpurse.data.DateRepository
import com.tpp.theperiodpurse.data.Symptom
import com.tpp.theperiodpurse.data.UserRepository
import com.tpp.theperiodpurse.ui.calendar.CalendarDayUIState
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Time
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val dateRepository: DateRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun loadData(calendarViewModel: CalendarViewModel) {
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

                    for (date in dates) {
                        if (date.date != null) {
                            // for it convert correctly we subtract 19 hours worth of milliseconds
                            var convertedExcLen = ""
                            if (date.exerciseLength != null){
                                convertedExcLen = Time(
                                    date.exerciseLength.toMillis()-19*36*100000
                                ).toString()
                            }

                            var convertedSleepLen = ""
                            if (date.sleep != null) {
                                convertedSleepLen = Time(
                                    date.sleep.toMillis()-19*36*100000
                                ).toString()
                            }

                            calendarViewModel.saveDayInfo(
                                date.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                CalendarDayUIState(
                                    flow = date.flow,
                                    mood = date.mood,
                                    exerciseLengthString = convertedExcLen,
                                    exerciseType = date.exerciseType,
                                    crampSeverity = date.crampSeverity,
                                    sleepString = convertedSleepLen
                                )
                            )
                        }
                    }
                }
            }

        }
    }

    fun getTrackedSymptoms() : List<Symptom> {
        return uiState.value.trackedSymptoms
    }

    fun setTackedSymptoms(trackedSymptoms: List<Symptom>) {
        _uiState.update { currentState ->
            currentState.copy(trackedSymptoms = trackedSymptoms)
        }
        val sympCopy = trackedSymptoms.toMutableList()
        sympCopy.remove(Symptom.FLOW)
        userRepository.setSymptoms(sympCopy)
    }

    fun updateSymptoms(symptom: Symptom): Boolean {
        val symptoms = getTrackedSymptoms()
        val sympCopy = symptoms.toMutableList()
        if(symptoms.contains(symptom)){
            sympCopy.remove(symptom)
            setTackedSymptoms(sympCopy.toList())
            return false
        } else{
            sympCopy.add(symptom)
            setTackedSymptoms(sympCopy.toList())
            return true
        }
    }

    fun isSymptomChecked(symptom: Symptom): Boolean{
        val symptoms = getTrackedSymptoms()
        return symptoms.contains(symptom)
    }

    fun getAllowReminders(): Boolean{
        return uiState.value.allowReminders
    }

    fun toggleAllowReminders(){
        val currentReminderState = _uiState.value.allowReminders
        _uiState.update { currentState -> currentState.copy(allowReminders = !currentReminderState)}
    }

    fun saveDate(date: Date) {
        dateRepository.addDate(date)
    }
}