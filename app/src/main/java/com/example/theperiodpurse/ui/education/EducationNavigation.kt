package com.example.theperiodpurse.ui.education

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.R

sealed class Destination(val route: String) {
    object Home: Destination("home")
    object DYK: Destination("dyk")
    object Privacy: Destination("privacy")
    object Terms: Destination("terms")
    object Info: Destination("info/{elementId}") {
        fun createRoute(elementId: String) = "info/$elementId"
    }
}

@Composable
fun EducationScreenLayout() {
    Surface(
    modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()
        NavigationAppHost(navController = navController)
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {

        composable(Destination.Home.route) { EducationScreen(navController) }
        composable(Destination.DYK.route) { EducationDYKScreen() }
        composable(Destination.Terms.route) { EducationTermsScreen() }
        composable(Destination.Privacy.route) { EducationPrivacyScreen() }
        composable(Destination.Info.route) { navBackStackEntry ->
            val elementId = navBackStackEntry.arguments?.getString("elementId")
            if (elementId == null) {
                println("ERROR")
            } else {
                EducationInfoScreen(elementId = elementId)
            }
        }

    }
}

@Composable
fun EducationBackground() {
    Image(painter = painterResource(R.drawable.colourwatercolour),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds)
}