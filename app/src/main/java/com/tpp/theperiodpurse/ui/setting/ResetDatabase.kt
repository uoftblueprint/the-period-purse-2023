package com.tpp.theperiodpurse.ui.setting


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.tpp.theperiodpurse.ui.state.AppUiState
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.ui.state.OnboardUIState
import com.tpp.theperiodpurse.ui.state.CalendarUIState
import com.tpp.theperiodpurse.ui.onboarding.LoadingScreen
import com.tpp.theperiodpurse.ui.viewmodel.OnboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A composable function for resetting the database and performing necessary cleanup.
 *
 * @param context The Android context.
 * @param viewModel The OnboardViewModel instance.
 * @param outController The NavHostController instance for the outer navigation graph.
 * @param navController The NavHostController instance for the current navigation graph.
 * @param onboardUiState The OnboardUIState object.
 * @param appUiState The AppUiState object.
 * @param calUiState The CalendarUIState object.
 * @param signout The sign out function to be called if the user is logged in with Google.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResetDatabase(
    context: Context,
    viewModel: OnboardViewModel,
    outController: NavHostController,
    onboardUiState: OnboardUIState,
    appUiState: AppUiState,
    calUiState: CalendarUIState,
    signout: () -> Unit = {}
) {
    // Observe the state of whether the database is deleted or not
    val isDeleted by viewModel.isOnboarded.observeAsState(initial = null)
    val confirmLoad = remember { mutableStateOf(false) }

    // Check the deleted status in the background
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            viewModel.checkDeletedStatus(context)
        }
    }

    // If the deletion status is not known, show a loading screen
    if (isDeleted == null) {
        LoadingScreen()
    } else {
        if (!confirmLoad.value) {
            // Reset necessary states and data
            calUiState.days = LinkedHashMap()
            appUiState.trackedSymptoms = listOf()
            appUiState.dates = emptyList()
            onboardUiState.days = 0
            onboardUiState.symptomsOptions = listOf()
            onboardUiState.date = ""
            onboardUiState.googleAccount = null

            // If the user is logged in with Google, sign out
            if (viewModel.checkGoogleLogin(context)) {
                signout()
            }

            // Navigate to the Welcome screen after resetting the database
            LaunchedEffect(Unit) {
                outController.navigate(OnboardingScreen.Welcome.name)
                confirmLoad.value = true
            }
        }
    }

}
