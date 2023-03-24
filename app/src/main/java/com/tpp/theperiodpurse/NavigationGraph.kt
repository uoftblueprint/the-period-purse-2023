package com.tpp.theperiodpurse

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.tpp.theperiodpurse.MainActivity
import com.tpp.theperiodpurse.data.*
import com.tpp.theperiodpurse.ui.SummaryScreen
import com.tpp.theperiodpurse.ui.calendar.CalendarScreen
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import com.tpp.theperiodpurse.ui.cycle.CycleScreenLayout
import com.tpp.theperiodpurse.ui.education.*
import com.tpp.theperiodpurse.ui.onboarding.*
import com.tpp.theperiodpurse.ui.setting.SettingsScreen
import com.tpp.theperiodpurse.ui.symptomlog.LogScreen
import java.time.Duration

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

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    calendarViewModel: CalendarViewModel,
    onboardViewModel: OnboardViewModel,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier,
    mainActivity: MainActivity,
    signIn: () -> Unit
) {
    val onboardUIState by onboardViewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Calendar.name) {
            CalendarScreen(
                navController = navController,
                appViewModel = appViewModel,
                calendarViewModel = calendarViewModel
            )
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
                    appViewModel = appViewModel,
                    calendarViewModel = calendarViewModel
                )
            }
        }

        composable(route = Screen.Settings.name) {
            SettingsScreen(appViewModel = appViewModel)
        }

        composable(route = Screen.Cycle.name) {
            CycleScreenLayout()
        }

        // Education Screens

        composable(route = Screen.Learn.name) {
            EducationScreenLayout()
        }



        // Welcome Screen
        composable(route = OnboardingScreen.Welcome.name) {
            WelcomeScreen(
                onNextButtonClicked =
                { navController.navigate(OnboardingScreen.QuestionOne.name) },
                signIn = signIn
            )
        }


        // Onboard Screens
        composable(route = OnboardingScreen.QuestionOne.name) {
            QuestionOneScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.QuestionTwo.name) },
                onSelectionChanged = { onboardViewModel.setQuantity(it.toInt()) },
                navigateUp = { navController.navigateUp() },
                canNavigateBack = navController.previousBackStackEntry != null,
                onboardUiState = onboardUIState,
            )
        }
        composable(route = OnboardingScreen.QuestionTwo.name) {
            QuestionTwoScreen(
                onboardUiState = onboardUIState,
                onNextButtonClicked = { navController.navigate(OnboardingScreen.QuestionThree.name) },
                onSelectionChanged = { onboardViewModel.setDate(it) },
                navigateUp = { navController.navigateUp() },
                canNavigateBack = navController.previousBackStackEntry != null
            )
        }

        composable(route = OnboardingScreen.QuestionThree.name) {
            QuestionThreeScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.Summary.name) },
                onSelectionChanged = { onboardViewModel.setSymptoms(it) },
                navigateUp = { navController.navigateUp() },
                canNavigateBack = navController.previousBackStackEntry != null
            )
        }
        composable(route = OnboardingScreen.Summary.name) {
            val symptomList = arrayListOf(Symptom.EXERCISE, Symptom.SLEEP, Symptom.MOOD, Symptom.CRAMPS);
            val currentDate = java.util.Date()
            val duration = Duration.ofHours(1)
            val dateList = arrayListOf(Date(2, currentDate, FlowSeverity.Heavy, Mood.ANGRY,
                duration, Exercise.YOGA, CrampSeverity.Bad, duration, ""))
            SummaryScreen(
                onboardUiState = onboardUIState,
                onSendButtonClicked = {
                    onboardViewModel.addNewDate(
                        currentDate,
                        FlowSeverity.Heavy,
                        Mood.ANGRY,
                        duration,
                        Exercise.YOGA,
                        CrampSeverity.Bad,
                        duration
                    )
                    onboardViewModel.addNewUser(symptomList, dateList, 0, 0, 0)
                    navController.popBackStack(OnboardingScreen.Welcome.name, inclusive = true)
                    navController.navigate(Screen.Calendar.name)
                    appViewModel.loadData(calendarViewModel)
                },
                navigateUp = { navController.navigateUp() },
                canNavigateBack = navController.previousBackStackEntry != null
//                onCancelButtonClicked = {
//                    cancelOrderAndNavigateToStart(onboardViewModel, navController)
//                },
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
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
