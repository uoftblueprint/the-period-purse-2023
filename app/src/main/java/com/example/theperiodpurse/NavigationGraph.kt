package com.example.theperiodpurse

import androidx.activity.result.ActivityResultRegistry
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.theperiodpurse.data.DataSource
import com.example.theperiodpurse.ui.QuestionThreeScreen
import com.example.theperiodpurse.ui.SummaryScreen
import com.example.theperiodpurse.ui.calendar.CalendarScreen
import com.example.theperiodpurse.ui.calendar.LogScreen
import com.example.theperiodpurse.ui.cycle.CycleScreenLayout
import com.example.theperiodpurse.ui.onboarding.OnboardViewModel
import com.example.theperiodpurse.ui.onboarding.QuestionOneScreen
import com.example.theperiodpurse.ui.onboarding.QuestionTwoScreen
import com.example.theperiodpurse.ui.onboarding.WelcomeScreen
import com.example.theperiodpurse.ui.setting.SettingPage
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
    viewModel: OnboardViewModel,
    modifier: Modifier = Modifier,
    mainActivity: MainActivity
) {
    val uiState by viewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Calendar.name) {
            CalendarScreen(navController = navController)
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
                    navController = navController
                )
            }
        }

        composable(route = Screen.Settings.name) {
            SettingPage(mainActivity)
        }

        composable(route = Screen.Cycle.name) {
            CycleScreenLayout()
        }

        composable(route = Screen.Learn.name) {
            /* TODO Put Screen for Info page here */
        }

        // Onboard Screens
        composable(route = OnboardingScreen.QuestionOne.name) {
            QuestionOneScreen(
                onNextButtonClicked = { navController.navigate(OnboardingScreen.QuestionTwo.name) },
                onSelectionChanged = { viewModel.setQuantity(it.toInt()) }
            )
        }
        composable(route = OnboardingScreen.QuestionTwo.name) {
            QuestionTwoScreen(
                onboardUiState = uiState,
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
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
