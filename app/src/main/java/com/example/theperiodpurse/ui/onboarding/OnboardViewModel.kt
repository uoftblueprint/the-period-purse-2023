package com.example.theperiodpurse.ui.onboarding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor (private val userRepository: UserRepository,
                                            private val dateRepository: DateRepository
                                            ): ViewModel() {
    private val _uiState = MutableStateFlow(OnboardUIState(dateOptions = dateOptions()))
    val uiState: StateFlow<OnboardUIState> = _uiState.asStateFlow()

    /**
     * Set the quantity [averageDays] for average period length for this onboarding session
     */
    fun setQuantity(numberDays: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                days = numberDays,

                )
        }
    }

    /**
     * Set the [logSymptoms] to track for onboarding session.
     */
    fun setSymptoms(logSymptoms: String) {
        var listOfSymptoms: MutableList<String> = mutableListOf()
        var tempList = logSymptoms.split("|")
        tempList.forEach { option ->
            if (option != "") {
                listOfSymptoms.add(option)
            }
        }

        _uiState.update { currentState ->
            currentState.copy(symptomsOptions = listOfSymptoms)
        }
    }

    /**
     * Set the [lastDate] of last period for current onboarding session
     */
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
            )
        }
    }

    /**
     * Reset the onboard state
     */
    fun resetOrder() {
        _uiState.value = OnboardUIState(dateOptions = dateOptions())
    }


    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */

    private fun dateOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 30 previous dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, -1)
        }
        dateOptions.reverse()
        return dateOptions
    }

    private fun createUser(symptomsToTrack: ArrayList<Symptom>, periodHistory: ArrayList<Date>,
                           averagePeriodLength: Int, averageCycleLength: Int,
                           daysSinceLastPeriod: Int): User {
        return User(
            symptomsToTrack = symptomsToTrack,
            periodHistory = periodHistory,
            averagePeriodLength = averagePeriodLength,
            averageCycleLength = averageCycleLength,
            daysSinceLastPeriod = daysSinceLastPeriod
        )
    }

    private fun saveUser(user: User) {
        userRepository.addUser(user)
    }

    fun addNewUser(symptomsToTrack: ArrayList<Symptom>, periodHistory: ArrayList<Date>,
                   averagePeriodLength: Int, averageCycleLength: Int, daysSinceLastPeriod: Int) {
        saveUser(createUser(symptomsToTrack, periodHistory, averagePeriodLength, averageCycleLength,
            daysSinceLastPeriod))
    }

    fun getAllUsers() {
        userRepository.getAllUsers()
    }

    private fun createDate(date: java.util.Date, flow: FlowSeverity, mood: Mood,
                           exerciseLength: java.util.Date, exerciseType: Exercise,
                           crampSeverity: CrampSeverity, sleep: java.util.Date): Date {
        return Date(
            date = date,
            flow = flow,
            mood = mood,
            exerciseLength = exerciseLength,
            exerciseType = exerciseType,
            crampSeverity = crampSeverity,
            sleep = sleep
        )
    }

    private fun saveDate(date: Date) {
        dateRepository.addDate(date)
    }

    fun addNewDate(date: java.util.Date, flow: FlowSeverity, mood: Mood,
                   exerciseLength: java.util.Date, exerciseType: Exercise,
                   crampSeverity: CrampSeverity, sleep: java.util.Date) {
        saveDate(createDate(date, flow, mood, exerciseLength, exerciseType, crampSeverity, sleep))
    }
}