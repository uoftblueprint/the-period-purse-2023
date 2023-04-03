package com.tpp.theperiodpurse.ui.setting


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.tpp.theperiodpurse.AppUiState
import com.tpp.theperiodpurse.AppViewModel
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.Screen
import com.tpp.theperiodpurse.data.OnboardUIState
import com.tpp.theperiodpurse.ui.calendar.CalendarUIState
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import com.tpp.theperiodpurse.ui.onboarding.LoadingScreen
import com.tpp.theperiodpurse.ui.onboarding.OnboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoadDatabase(
    navController: NavHostController,
    appViewModel: AppViewModel,
    calViewModel: CalendarViewModel,
) {
    val isLoaded by appViewModel.isLoaded.observeAsState(initial = null)


    appViewModel.loadData(calViewModel)

    if (isLoaded == null){
        LoadingScreen()
    }
    else {
        LaunchedEffect(Unit) {
            navController.popBackStack(OnboardingScreen.Welcome.name, inclusive = true)
            navController.navigate(Screen.Calendar.name)
        }

    }

}