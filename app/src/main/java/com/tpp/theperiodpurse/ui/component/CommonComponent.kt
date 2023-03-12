
package com.tpp.theperiodpurse.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.tpp.theperiodpurse.Screen
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.currentRoute
import com.tpp.theperiodpurse.ui.theme.Red
import com.tpp.theperiodpurse.ui.theme.Teal

@Composable
fun BottomNavigation(navController: NavController) {
    BottomNavigation(
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
    )
}

@Composable
private fun BottomNavigation(
    onInfoNavigationClicked: () -> Unit,
    onSettingsNavigationClicked: () -> Unit,
    modifier: Modifier = Modifier,
    navItemModifier: Modifier = Modifier
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


    }
}
@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    contentColor: Color = Color.White,
    onClickInCalendar: () -> Unit
) {
    val circle = MaterialTheme.shapes.large.copy(CornerSize(percent = 50))
    var iconId by remember { mutableStateOf(R.drawable.add_black_24dp) }
    var backgroundColor by remember { mutableStateOf(Red) }
    var contentId by remember { mutableStateOf(R.string.fab_to_calendar) }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.route == Screen.Calendar.name) {
            iconId = R.drawable.add_black_24dp
            backgroundColor = Red
            contentId = R.string.fab_see_log_options
        } else {
            iconId = R.drawable.today_black_24dp
            backgroundColor = Teal
            contentId = R.string.fab_to_calendar
        }
    }
    if (currentRoute(navController) in Screen.values().map{ it.name }) {
        FloatingActionButton(
            onClick =
            {
                if (navController.currentDestination?.route == Screen.Calendar.name) {
                    onClickInCalendar()
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
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            modifier = modifier
                .padding(14.dp)
                .border(color = Color.White, width = 2.dp, shape = circle)
                .size(70.dp)
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = stringResource(contentId),
                modifier = Modifier
                    .width(30.dp)
                    .aspectRatio(1f)
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationPreview() {
    BottomNavigation({}, {})
}


