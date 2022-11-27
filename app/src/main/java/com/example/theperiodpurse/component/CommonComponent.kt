package com.example.theperiodpurse.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.theperiodpurse.AppScreen
import com.example.theperiodpurse.R

class CommonComponent {
}
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
                modifier = Modifier.width(0.dp)
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
fun FloatingActionButton(currentDestination: NavDestination?): Unit {
    var fabIcon by remember { mutableStateOf(0) }
    FloatingActionButton(
        onClick = { },
        shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
        contentColor = Color.White,
        backgroundColor = Color.Red,
        modifier = Modifier.border(color = Color.White, width = 2.dp, shape=MaterialTheme.shapes.large.copy(
            CornerSize(percent =50)
        ))
    ) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = "fab")
//        Icon(
//            painter = painterResource(id = R.drawable.add_circle_outline_black_24dp),
//            contentDescription = "fab",
//            modifier = Modifier.width(56.dp).aspectRatio(1f)
//        )
    }
}

@Composable
fun BottomAppBar(navController: NavController): Unit {
    BottomAppBar(
        backgroundColor = Color.White
    ) {

        IconButton(onClick = { navController.navigate(AppScreen.Learn.name) }) {
            Icon(painterResource(id = R.drawable.info_black_24dp), contentDescription = "Localized description")
        }
        FloatingActionButton(
            onClick = { },
            shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50))
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "fab")
        }

        IconButton(onClick = { navController.navigate(AppScreen.Settings.name) }) {
            Icon(painterResource(id = R.drawable.settings_black_24dp), contentDescription = "Localized description")
        }
    }
}
