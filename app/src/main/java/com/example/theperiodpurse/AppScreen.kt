package com.example.theperiodpurse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.example.theperiodpurse.ui.component.BottomNavigation
import com.example.theperiodpurse.ui.component.FloatingActionButton

enum class AppScreen() {
    Calendar,
    Cycle,
    Settings,
    Learn
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var fabIcon by remember { mutableStateOf(R.drawable.add_circle_outline_black_24dp) }
    var backgroundColor by remember {
        mutableStateOf(Color(0xFFBF3428))
    }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.route == AppScreen.Calendar.name) {
            fabIcon = R.drawable.add_black_24dp
            backgroundColor = Color(0xFFBF3428)
        } else {
            fabIcon = R.drawable.today_black_24dp
            backgroundColor = Color(0xFF72C6B7)
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(navController, fabIcon, backgroundColor)
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },

        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
    ) {
        NavigationGraph(navController = navController)
    }
}
