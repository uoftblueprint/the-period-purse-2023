package com.example.theperiodpurse

import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.DateRepository
import com.example.theperiodpurse.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor (private val userRepository: UserRepository,
                                        private val dateRepository: DateRepository
                                        ): ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState(

    ))
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private fun test() {

    }
}