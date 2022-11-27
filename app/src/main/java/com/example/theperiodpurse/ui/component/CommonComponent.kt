
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
import com.example.theperiodpurse.Screen
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.theme.Red100
import com.example.theperiodpurse.ui.theme.Teal100

@Composable
fun BottomNavigation(navController: NavController) {
    var fabIconId by remember { mutableStateOf(R.drawable.add_circle_outline_black_24dp) }
    var fabBackgroundColor by remember { mutableStateOf(Red100) }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.route == Screen.Calendar.name) {
            fabIconId = R.drawable.add_black_24dp
            fabBackgroundColor = Red100
        } else {
            fabIconId = R.drawable.today_black_24dp
            fabBackgroundColor = Teal100
        }
    }

    BottomNavigationWithFAB(
        onInfoNavigationClicked = {
            navController.navigate(Screen.Learn.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onSettingsNavigationClicked = {
            navController.navigate(Screen.Settings.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onFABClicked = {
            if (navController.currentDestination?.route == Screen.Calendar.name) {
                /* TODO onAddActionClicked */
            } else {
                navController.navigate(Screen.Calendar.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
        fabIconId = fabIconId,
        fabBackgroundColor = fabBackgroundColor
    )
}

@Composable
private fun BottomNavigationWithFAB(
    onInfoNavigationClicked: () -> Unit,
    onSettingsNavigationClicked: () -> Unit,
    onFABClicked: () -> Unit,
    fabIconId: Int,
    fabBackgroundColor: Color,
    modifier: Modifier = Modifier,
    navItemModifier: Modifier = Modifier,
    fabModifier: Modifier = Modifier
) {
    Box {
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
                label = { Text(Screen.Learn.name) },
                selected = false,
                onClick = onInfoNavigationClicked,
                modifier = navItemModifier
            )

            Spacer(modifier = modifier.width(58.dp))

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.settings_black_24dp),
                        contentDescription = null
                    )
                },
                label = { Text(Screen.Settings.name) },
                selected = false,
                onClick = onSettingsNavigationClicked,
                modifier = navItemModifier
            )
        }

        FloatingActionButton(
            onClick = onFABClicked,
            iconId = fabIconId,
            backgroundColor = fabBackgroundColor,
            modifier = fabModifier
                .align(Alignment.BottomCenter)
                .padding(15.dp)
        )

    }
}

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    iconId: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.White
) {
    val circle = MaterialTheme.shapes.large.copy(CornerSize(percent = 50))
    FloatingActionButton(
        onClick = onClick,
        shape = circle,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        modifier = modifier
            .border(color = Color.White, width = 2.dp, shape = circle)
            .size(70.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
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
    BottomNavigationWithFAB({}, {}, {}, R.drawable.today_black_24dp, Teal100)
}

@Preview
@Composable
fun BottomNavigationPreviewCalendar() {
    BottomNavigationWithFAB({}, {}, {}, R.drawable.add_black_24dp, Red100)
}

