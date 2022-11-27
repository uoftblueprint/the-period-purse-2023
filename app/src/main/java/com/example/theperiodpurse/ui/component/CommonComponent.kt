
package com.example.theperiodpurse.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.theperiodpurse.AppScreen
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.theme.Red100
import com.example.theperiodpurse.ui.theme.Teal100

@Composable
fun BottomNavigation(navController: NavController) {
    var fabIcon by remember { mutableStateOf(R.drawable.add_circle_outline_black_24dp) }
    var fabBackgroundColor by remember { mutableStateOf(Red100) }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.route == AppScreen.Calendar.name) {
            fabIcon = R.drawable.add_black_24dp
            fabBackgroundColor = Red100
        } else {
            fabIcon = R.drawable.today_black_24dp
            fabBackgroundColor = Teal100
        }
    }

    BottomNavigation_(
        onInfoNavigationClicked = {
            navController.navigate(AppScreen.Learn.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onSettingsNavigationClicked = {
            navController.navigate(AppScreen.Settings.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onFABClicked = {
            navController.navigate(AppScreen.Calendar.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        fabIcon = fabIcon,
        fabBackgroundColor = fabBackgroundColor
    )
}

@Composable
private fun BottomNavigation_(
    onInfoNavigationClicked: () -> Unit,
    onSettingsNavigationClicked: () -> Unit,
    onFABClicked: () -> Unit,
    fabIcon: Int,
    fabBackgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box() {
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = modifier.align(Alignment.BottomCenter)
        ) {

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.info_black_24dp),
                        contentDescription = null
                    )
                },
                label = { Text(AppScreen.Learn.name) },
                selected = false,
                onClick = onInfoNavigationClicked,
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
                selected = false,
                onClick = onSettingsNavigationClicked,
            )
        }

        FloatingActionButton(
            onClick = onFABClicked,
            id = fabIcon,
            backgroundColor = fabBackgroundColor,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(15.dp)
        )

    }
}

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    id: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val circle = MaterialTheme.shapes.large.copy(CornerSize(percent = 50))
    FloatingActionButton(
        onClick = onClick,
        shape = circle,
        contentColor = Color.White,
        backgroundColor = backgroundColor,
        modifier = modifier
            .border(color = Color.White, width = 2.dp, shape = circle)
            .size(70.dp)
    ) {
        Icon(
            painter = painterResource(id),
            contentDescription = "fab",
            modifier = Modifier
                .width(30.dp)
                .aspectRatio(1f)
        )
    }
}

@Preview
@Composable
fun BottomNavigationPreviewNonCalendar() {
    BottomNavigation_({}, {}, {}, R.drawable.today_black_24dp, Teal100)
}

@Preview
@Composable
fun BottomNavigationPreviewCalendar() {
    BottomNavigation_({}, {}, {}, R.drawable.add_black_24dp, Red100)
}

