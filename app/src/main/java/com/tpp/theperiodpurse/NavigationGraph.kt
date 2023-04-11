package com.tpp.theperiodpurse

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.tpp.theperiodpurse.ui.symptomlog.LogMultipleDatesScreen
import com.tpp.theperiodpurse.data.*
import com.tpp.theperiodpurse.ui.SummaryScreen
import com.tpp.theperiodpurse.ui.calendar.CalendarScreen
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import com.tpp.theperiodpurse.ui.cycle.CycleScreenLayout
import com.tpp.theperiodpurse.ui.cycle.PeriodHistoryLayout
import com.tpp.theperiodpurse.ui.education.*
import com.tpp.theperiodpurse.ui.legal.PrivacyScreen
import com.tpp.theperiodpurse.ui.legal.TermsScreen
import com.tpp.theperiodpurse.ui.onboarding.*
import com.tpp.theperiodpurse.ui.setting.LoadDatabase
import com.tpp.theperiodpurse.ui.setting.SettingsScreen
import com.tpp.theperiodpurse.ui.symptomlog.LogScreen
import java.time.LocalDate

enum class Screen {
    Calendar,
    Log,
    Cycle,
    Settings,
    Learn,
    LogMultipleDates,
    CycleFullHisotry
}

enum class LegalScreen {
    Terms,
    Privacy
}

val screensWithNavigationBar = arrayOf(
    Screen.Calendar.name, Screen.Log.name, Screen.Cycle.name,
    Screen.Settings.name, Screen.Learn.name, Screen.CycleFullHisotry.name
)

enum class OnboardingScreen {
    Welcome,
    QuestionOne,
    QuestionTwo,
    QuestionThree,
    Summary,
    LoadGoogleDrive,
    LoadDatabase,
    DownloadBackup
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    calendarViewModel: CalendarViewModel,
    onboardViewModel: OnboardViewModel,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier,
    context: Context,
    signIn: () -> Unit,
    signout: () -> Unit = {},
    isOnboarded: Boolean,
    hasNotificationsPermission: Boolean
) {
    val onboardUIState by onboardViewModel.uiState.collectAsState()
    val appUiState by appViewModel.uiState.collectAsState()
    val calUiState by calendarViewModel.uiState.collectAsState()
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
                    calendarViewModel = calendarViewModel,
                    context = context
                )
            }
        }

        composable(route = Screen.LogMultipleDates.name) {
            LogMultipleDatesScreen(
                onClose = { navController.navigateUp() },
                calendarViewModel,
                appViewModel
            )
        }

        composable(route = Screen.Settings.name) {
            SettingsScreen(appViewModel = appViewModel,
                outController = navController,
                context = context,
                onboardUiState = onboardUIState,
                onboardViewModel = onboardViewModel,
                appUiState = appUiState,
                calUiState = calUiState,
                signIn = signIn,
                signout = signout)
        }

        composable(route = Screen.Cycle.name) {
            CycleScreenLayout(
                appViewModel = appViewModel,
                navController = navController
            )
        }

        composable(route = Screen.CycleFullHisotry.name) {
            PeriodHistoryLayout(
                appViewModel = appViewModel,
                navController = navController
            )
        }

        // Education Screens

        composable(route = Screen.Learn.name) {
            EducationScreen(outController = navController)
        }

        composable(LegalScreen.Terms.name) {
            TermsScreen(navController)
        }

        composable(LegalScreen.Privacy.name) {
            PrivacyScreen(navController)
        }


        // Welcome Screen
        composable(route = OnboardingScreen.Welcome.name) {
            WelcomeScreen(
                onNextButtonClicked =
                { navController.navigate(OnboardingScreen.QuestionOne.name) },
                signIn = signIn,
                navController = navController,
                context = context,
                onboardUIState = onboardUIState,
            )
        }


        // Onboard Screens
        composable(route = OnboardingScreen.QuestionOne.name) {
            QuestionOneScreen(
                navController = navController,
                onSelectionChanged = { onboardViewModel.setQuantity(it.toInt()) },
                navigateUp = { navController.navigateUp() },
                canNavigateBack = navController.previousBackStackEntry != null,
                onboardUiState = onboardUIState,
                viewModel = onboardViewModel,
                signOut = signout,
                context = context
            )
        }
        composable(route = OnboardingScreen.QuestionTwo.name) {
            QuestionTwoScreen(
                navController = navController,
                onboardUiState = onboardUIState,
                onSelectionChanged = { onboardViewModel.setDate(it) },
                navigateUp = { navController.navigateUp() },
                canNavigateBack = navController.previousBackStackEntry != null
            )
        }

        composable(route = OnboardingScreen.QuestionThree.name) {
            QuestionThreeScreen(
                navController = navController,
                onboardUiState = onboardUIState,
                onSelectionChanged = { onboardViewModel.setSymptoms(it) },
                canNavigateBack = navController.previousBackStackEntry != null
            )
        }
        composable(route = OnboardingScreen.Summary.name) {
            SummaryScreen(
                onboardUiState = onboardUIState,
                onSendButtonClicked = {
                    navController.navigate(OnboardingScreen.LoadDatabase.name)

                },
                navigateUp = { navController.navigateUp() },
                canNavigateBack = navController.previousBackStackEntry != null,
                viewModel = onboardViewModel,
                context = context
//                onCancelButtonClicked = {
//                    cancelOrderAndNavigateToStart(onboardViewModel, navController)
//                },
            )
        }

        composable(route = OnboardingScreen.LoadDatabase.name) {

//            var hasNotificationPermission by remember {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    mutableStateOf(
//                        ContextCompat.checkSelfPermission(
//                            context,
//                            Manifest.permission.POST_NOTIFICATIONS
//                        ) == PackageManager.PERMISSION_GRANTED
//                    )
//                } else mutableStateOf(true)
//            }

            LoadDatabase(
                appViewModel = appViewModel,
                calViewModel = calendarViewModel,
                navController = navController,
                context = context,
                hasNotificationsPermissions = hasNotificationsPermission,
                isOnboarded = isOnboarded
            )
        }
        composable(route = OnboardingScreen.LoadGoogleDrive.name) {
                LoadGoogleDrive(
                    viewModel = onboardViewModel,
                    navHostController = navController,
                    context = context,
                    googleAccount = onboardUIState.googleAccount)
        }

        composable(route = OnboardingScreen.DownloadBackup.name) {
            DownloadBackup(googleAccount = onboardUIState.googleAccount,
                viewModel = onboardViewModel,
                navHostController = navController,
                context = context)
        }

    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun navigateToLogScreenWithDate(date: LocalDate, navController: NavController) {
    navController.navigate(route = "%s/%s/%s"
        .format(Screen.Calendar, Screen.Log, date.toString()))
}
