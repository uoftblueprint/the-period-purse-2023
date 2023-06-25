package com.tpp.theperiodpurse.ui.setting


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResetDatabase(
    context: Context,
    viewModel: OnboardViewModel,
    outController: NavHostController,
    navController: NavHostController,
    onboardUiState: OnboardUIState,
    appUiState: AppUiState,
    calUiState: CalendarUIState,
    signout: () -> Unit = {}
) {

    val isDeleted by viewModel.isOnboarded.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            viewModel.checkDeletedStatus(context)
        }
    }
    if (isDeleted == null) {
        LoadingScreen()
    } else {
        calUiState.days = LinkedHashMap()
        appUiState.trackedSymptoms = listOf()
        appUiState.dates = emptyList()
        onboardUiState.days = 0
        onboardUiState.symptomsOptions = listOf()
        onboardUiState.date = ""
        onboardUiState.googleAccount = null

        if (viewModel.checkGoogleLogin(context)) {
            signout()
        }

        LaunchedEffect(Unit) {
            outController.navigate(OnboardingScreen.Welcome.name)
        }


    }

}
