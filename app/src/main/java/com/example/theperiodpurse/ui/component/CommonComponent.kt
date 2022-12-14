package com.example.theperiodpurse.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.OnboardingScreen
import com.example.theperiodpurse.R
import com.example.theperiodpurse.Screen
import com.example.theperiodpurse.currentRoute
import com.example.theperiodpurse.ui.theme.Teal

@Composable
fun BottomNavigationWithFAB(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
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
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        if (currentRoute(navController) != null && currentRoute(navController) != Screen.Calendar.name) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Calendar.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                iconId = R.drawable.today_black_24dp,
                backgroundColor = Teal,
                contentDescription = stringResource(R.string.fab_to_calendar),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.fab_bottom_padding))
            )
        }
    }
}

@Composable
private fun BottomNavigation(
    onInfoNavigationClicked: () -> Unit,
    onSettingsNavigationClicked: () -> Unit,
    modifier: Modifier = Modifier,
    navItemModifier: Modifier = Modifier,
) {
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = modifier
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

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    iconId: Int,
    contentDescription: String?,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.White,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation()
) {
    val circle = MaterialTheme.shapes.large.copy(CornerSize(percent = 50))
    FloatingActionButton(
        onClick = onClick,
        shape = circle,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        elevation = elevation,
        modifier = modifier
            .border(color = Color.White, width = 2.dp, shape = circle)
            .size(70.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = contentDescription,
            modifier = Modifier
                .width(30.dp)
                .aspectRatio(1f)
        )
    }
}

@Preview
@Composable
fun BottomNavigationPreviewNonCalendar() {
//    BottomNavigationWithFAB(rememberNavController(), Screen.Calendar.name)
    BottomNavigationWithFAB(rememberNavController())
}

@Preview
@Composable
fun BottomNavigationPreviewCalendar() {
    BottomNavigationWithFAB(rememberNavController())
}

@Preview
@Composable
fun Fab() {
//    FloatingActionButton({}, R.drawable.add_black_24dp, null, Teal, )
    val navController = rememberNavController()
    FloatingActionButton(
        onClick = {
            navController.navigate(Screen.Calendar.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        iconId = R.drawable.today_black_24dp,
        backgroundColor = Teal,
        contentDescription = stringResource(R.string.fab_to_calendar),
        modifier = Modifier
            .padding()
    )
}



