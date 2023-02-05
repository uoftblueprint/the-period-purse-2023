package com.example.theperiodpurse

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.theperiodpurse.data.*
import com.example.theperiodpurse.ui.QuestionThreeScreen
import com.example.theperiodpurse.ui.SummaryScreen
import com.example.theperiodpurse.ui.calendar.CalendarScreen
import com.example.theperiodpurse.ui.calendar.LogScreen
import com.example.theperiodpurse.ui.cycle.CycleScreenLayout
import com.example.theperiodpurse.ui.onboarding.OnboardViewModel
import com.example.theperiodpurse.ui.onboarding.QuestionOneScreen
import com.example.theperiodpurse.ui.onboarding.QuestionTwoScreen
import com.example.theperiodpurse.ui.onboarding.WelcomeScreen
import com.example.theperiodpurse.ui.setting.SettingsScreen

enum class Screen() {
    Calendar,
    Log,
    Cycle,
    Settings,
    Learn
}

enum class OnboardingScreen() {
    Welcome,
    QuestionOne,
    QuestionTwo,
    QuestionThree,
    Summary,
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    onboardViewModel: OnboardViewModel,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val onboardUIState by onboardViewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Calendar.name) {
            CalendarScreen(navController = navController, appViewModel)
        }

        composable(
            route = "%s/%s/{date}"
                .format(Screen.Calendar, Screen.Log),
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            // date is in yyyy-mm-dd format
            val date = backStackEntry.arguments?.getString("date")
            if (date != null) {
                LogScreen(
                    date = date,
                    navController = navController,
                    appViewModel
                )
            }
        }

        composable(route = Screen.Settings.name) {
            SettingsScreen(appViewModel = appViewModel,)
        }

        composable(route = Screen.Cycle.name) {
            CycleScreenLayout(appViewModel)
        }

        composable(route = Screen.Learn.name) {
            /* TODO Put Screen for Info page here */
        }

        // Onboard Screens

        composable(route = OnboardingScreen.Welcome.name) {
            WelcomeScreen(
                onNextButtonClicked = {
                    navController.navigate(OnboardingScreen.QuestionOne.name)
                }
            )
        }
        composable(route = OnboardingScreen.QuestionOne.name) {
            QuestionOneScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.QuestionTwo.name) },
                onSelectionChanged = { onboardViewModel.setQuantity(it.toInt()) }
            )
        }
        composable(route = OnboardingScreen.QuestionTwo.name) {
            QuestionTwoScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.QuestionThree.name) },
                options = onboardUIState.dateOptions,
                onSelectionChanged = { onboardViewModel.setDate(it) }
            )
        }

        composable(route = OnboardingScreen.QuestionThree.name) {
            val context = LocalContext.current
            QuestionThreeScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.Summary.name) },
                onSelectionChanged = { onboardViewModel.setSymptoms(it) },
                options = DataSource.symptoms.map { id -> context.resources.getString(id) },
            )
        }
        composable(route = OnboardingScreen.Summary.name) {
            val symptomList = arrayListOf(Symptom.MOOD);
            val currentDate = java.util.Date()
            val dateList = arrayListOf(Date(2, currentDate, FlowSeverity.HEAVY, Mood.ANGRY,
                currentDate, Exercise.YOGA, CrampSeverity.Bad, currentDate))
            SummaryScreen(
                onboardUiState = onboardUIState,
                onSendButtonClicked = {
                    onboardViewModel.addNewDate(dateList[0].date, dateList[0].flow, dateList[0].mood,
                        dateList[0].exerciseLength, dateList[0].exerciseType,
                        dateList[0].crampSeverity, dateList[0].sleep)
                    onboardViewModel.addNewUser(symptomList, dateList, 0, 0, 0)
                    navController.popBackStack(OnboardingScreen.Welcome.name, inclusive = true)
                    navController.navigate(Screen.Calendar.name)
                },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(onboardViewModel, navController)
                },
            )
        }
    }
}

/**
 * Resets the [OnboardUIState] and pops up to [OnboardingScreen.Start]
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: OnboardViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(OnboardingScreen.Welcome.name, inclusive = false)
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
