package com.example.theperiodpurse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.theperiodpurse.data.DataSource
import com.example.theperiodpurse.ui.QuestionThreeScreen
import com.example.theperiodpurse.ui.SummaryScreen
import com.example.theperiodpurse.ui.calendar.CalendarScreen
import com.example.theperiodpurse.ui.cycle.CycleScreenLayout
import com.example.theperiodpurse.ui.onboarding.OnboardViewModel
import com.example.theperiodpurse.ui.onboarding.QuestionOneScreen
import com.example.theperiodpurse.ui.onboarding.QuestionTwoScreen
import com.example.theperiodpurse.ui.onboarding.WelcomeScreen
import com.example.theperiodpurse.ui.setting.SettingScreen

enum class Screen() {
    Calendar,
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

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    viewModel: OnboardViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Calendar.name) {
            CalendarScreen()
        }

        composable(route = Screen.Settings.name) {
            SettingScreen()
        }

        composable(route = Screen.Cycle.name) {
            CycleScreenLayout()
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
                onSelectionChanged = { viewModel.setQuantity(it.toInt()) }
            )
        }
        composable(route = OnboardingScreen.QuestionTwo.name) {
            QuestionTwoScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.QuestionThree.name) },
                options = uiState.dateOptions,
                onSelectionChanged = { viewModel.setDate(it) }
            )
        }

        composable(route = OnboardingScreen.QuestionThree.name) {
            val context = LocalContext.current
            QuestionThreeScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.Summary.name) },
                onSelectionChanged = { viewModel.setSymptoms(it) },
                options = DataSource.symptoms.map { id -> context.resources.getString(id) },
            )
        }
        composable(route = OnboardingScreen.Summary.name) {
            SummaryScreen(
                onboardUiState = uiState,
                onSendButtonClicked = {
                    navController.popBackStack(OnboardingScreen.Welcome.name, inclusive = true)
                    navController.navigate(Screen.Calendar.name)
                },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
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
