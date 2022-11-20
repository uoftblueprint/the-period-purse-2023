package com.example.theperiodpurse.ui.onboarding

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import android.content.Context
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.DataSource.symptoms
import com.example.theperiodpurse.ui.QuestionThreeScreen
import com.example.theperiodpurse.ui.SummaryScreen
import com.example.theperiodpurse.ui.onboarding.*



enum class OnboardingScreen(@StringRes val title: Int) {
    Welcome(title = R.string.app_name),
    QuestionOne(title = R.string.title_onboard_one),
    QuestionTwo(title = R.string.title_onboard_two),
    QuestionThree(title = R.string.title_onboard_three),
    Summary(title = R.string.title_onboard_summary),



}


@Composable
fun OnboardingScreenBar(
    currentScreen: OnboardingScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )

}

@Composable
fun OnboardApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    navController: NavHostController = rememberNavController()

) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = OnboardingScreen.valueOf(
        backStackEntry?.destination?.route ?: OnboardingScreen.Welcome.name
    )
    Scaffold(
        topBar = {
            OnboardingScreenBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
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
                    onNextButtonClicked = { navController.navigate(OnboardingScreen.QuestionTwo.name)},
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
                    options = symptoms.map { id -> context.resources.getString(id) },
                )
            }
            composable(route = OnboardingScreen.Summary.name) {
                SummaryScreen(
                    onboardUiState = uiState,
                    onSendButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
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


