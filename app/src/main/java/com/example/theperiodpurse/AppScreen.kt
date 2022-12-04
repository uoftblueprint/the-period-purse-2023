package com.example.theperiodpurse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.theperiodpurse.ui.onboarding.*
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.example.theperiodpurse.ui.component.BottomNavigation

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

@Composable
fun ScreenApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            if (!OnboardingScreen.values().any { currentRoute(navController) == it.name }) {
                BottomNavigation(navController = navController)
            }
        }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            startDestination = OnboardingScreen.Welcome.name,
            viewModel,
            modifier = modifier.padding(innerPadding)
        )
    }
}
