package com.example.theperiodpurse

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.theperiodpurse.ui.calendar.CalendarViewModel
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.data.OnboardUIState
import com.example.theperiodpurse.ui.component.BottomNavigation
import com.example.theperiodpurse.ui.component.FloatingActionButton
import com.example.theperiodpurse.ui.onboarding.*
import com.example.theperiodpurse.ui.symptomlog.LoggingOptionsPopup
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import java.time.LocalDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                Application()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Application() {
    ScreenApp()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    calendarViewModel: CalendarViewModel = viewModel(),
    skipOnboarding: Boolean = false,
    navController: NavHostController = rememberNavController()
) {

    var skipOnboard = skipOnboarding

//    skipOnboard = viewModel.userOnboarded()




    var loggingOptionsVisible by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            if (currentRoute(navController) in Screen.values().map { it.name }) {
                BottomNavigation(navController = navController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                navController = navController,
                onClickInCalendar = { loggingOptionsVisible = true }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Box {
            NavigationGraph(
                navController = navController,
                startDestination = if (skipOnboard) Screen.Calendar.name else OnboardingScreen.Welcome.name,
                viewModel = viewModel,
                calendarViewModel = calendarViewModel,
                modifier = modifier.padding(innerPadding),
            )

            if (loggingOptionsVisible) {
                LoggingOptionsPopup(
                    onLogDailySymptomsClick = {
                        navController.navigate(
                            route = "%s/%s/%s"
                                .format(
                                    Screen.Calendar,
                                    Screen.Log,
                                    LocalDate.now().toString()
                                )
                        )
                    },
                    { /* TODO: Go to logging page for multiple dates */ },
                    onExit = { loggingOptionsVisible = false },
                    modifier = modifier.padding(innerPadding)
                )
            }
        }
    }
}
