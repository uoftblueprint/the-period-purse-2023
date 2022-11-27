package com.example.theperiodpurse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.data.DataSource
import com.example.theperiodpurse.ui.QuestionThreeScreen
import com.example.theperiodpurse.ui.SummaryScreen
import com.example.theperiodpurse.ui.cycle.CycleScreenLayout
import com.example.theperiodpurse.ui.onboarding.*
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                Application()

            }
        }
    }
}

@Composable
fun Application() {
    ScreenApp()
}

enum class OnboardingScreen(@StringRes val title: Int) {
    Welcome(title = R.string.app_name),
    QuestionOne(title = R.string.title_onboard_one),
    QuestionTwo(title = R.string.title_onboard_two),
    QuestionThree(title = R.string.title_onboard_three),
    Summary(title = R.string.title_onboard_summary),
    Calendar(title = R.string.title_calendar_screen)


}

@Composable
fun ScreenApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    navController: NavHostController = rememberNavController()

) {
    Scaffold(
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = OnboardingScreen.Welcome.name,
            modifier = modifier.padding(innerPadding)
        ) {
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
                        navController.navigate(OnboardingScreen.Calendar.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                )
            }
            composable(route = OnboardingScreen.Calendar.name) {
                CycleScreenLayout(
                )
            }


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
