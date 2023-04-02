package com.tpp.theperiodpurse.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.Screen
import com.tpp.theperiodpurse.ui.theme.HeaderColor1
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
    Column() {
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
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(6.dp)
        ) {}
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


@Composable
fun PopupTopBar(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(color = HeaderColor1)
            .height(100.dp)
            .padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp)
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Button"
                )
            }
        }
        content()
    }
}


@Preview
@Composable
fun BottomNavigationPreview() {
    BottomNavigation({}, {})
}

@Preview
@Composable
fun PopupTopBarPreview() {
    PopupTopBar({}) {
    }
}

