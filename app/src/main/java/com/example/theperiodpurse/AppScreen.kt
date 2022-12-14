package com.example.theperiodpurse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.theperiodpurse.ui.onboarding.*
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.example.theperiodpurse.ui.component.BottomNavigationWithFAB

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
    ScreenApp(skipOnboarding = true)
}

@Composable
fun ScreenApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    skipOnboarding: Boolean = false,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            val currentRoute = currentRoute(navController)
            if (currentRoute in Screen.values().map{ it.name }) {
                BottomNavigationWithFAB(
                    navController = navController,
                )
            }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize()) {
            val currentRoute = currentRoute(navController)
            if (currentRoute in Screen.values().map{ it.name }) {
//                BottomNavigationWithFAB(
//                    navController = navController,
//                    modifier = Modifier.align(Alignment.BottomCenter)
//                )
            }
            NavigationGraph(
                navController = navController,
                startDestination = if (skipOnboarding) Screen.Calendar.name else OnboardingScreen.Welcome.name,
                viewModel,
                modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding())
            )
        }
    }
}
