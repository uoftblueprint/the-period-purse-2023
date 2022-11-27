package com.example.theperiodpurse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.example.theperiodpurse.component.BottomNavigation
import com.example.theperiodpurse.component.FloatingActionButton

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
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(navController.currentDestination)
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },

        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
//        bottomBar = { BottomAppBar(navController = navController) }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ThePeriodPurseTheme {
        Greeting("Android")
    }
}