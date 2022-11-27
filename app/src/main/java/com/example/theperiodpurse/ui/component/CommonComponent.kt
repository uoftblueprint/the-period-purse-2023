
package com.example.theperiodpurse.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.theperiodpurse.AppScreen
import com.example.theperiodpurse.R

@Composable
fun BottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Box() {

        BottomNavigation(
            backgroundColor = Color.White,
        ) {

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.info_black_24dp),
                        contentDescription = null
                    )
                },
                label = { Text(AppScreen.Learn.name) },
                selected = currentDestination?.hierarchy?.any { it.route == AppScreen.Settings.name } == true,
                onClick = {
                    navController.navigate(AppScreen.Learn.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )

            BottomNavigationItem(
                icon = {},
                label = null,
                selected = false,
                onClick = {},
                enabled = false,
                modifier = Modifier.weight(0.35f)
            )

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.settings_black_24dp),
                        contentDescription = null
                    )
                },
                label = { Text(AppScreen.Settings.name) },
                selected = currentDestination?.hierarchy?.any { it.route == AppScreen.Settings.name } == true,
                onClick = {
                    navController.navigate(AppScreen.Settings.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
fun FloatingActionButton(id: Int, backgroundColor: Color) {
    val circle = MaterialTheme.shapes.large.copy(CornerSize(percent = 50))
    FloatingActionButton(
        onClick = { },
        shape = circle,
        contentColor = Color.White,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .border(color = Color.White, width = 2.dp, shape = circle)
            .size(70.dp)
    ) {
        Icon(
            painter = painterResource(id),
            contentDescription = "fab",
            modifier = Modifier.width(30.dp).aspectRatio(1f)
        )
    }
}


