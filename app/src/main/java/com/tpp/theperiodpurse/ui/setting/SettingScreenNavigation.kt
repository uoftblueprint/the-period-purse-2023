package com.tpp.theperiodpurse.ui.setting

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.AppViewModel
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.Symptom

enum class SettingScreenNavigation(@StringRes val title: Int) {
    Start(title = R.string.settings_home),
    Notification(title = R.string.customize_notifications),
    BackUpAccount(title = R.string.back_up_account),
    DeleteAccount(title = R.string.delete_account)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun SettingAppBar(
    currentScreen: SettingScreenNavigation,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    appViewModel: AppViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SettingScreenNavigation.valueOf(
        backStackEntry?.destination?.route ?: SettingScreenNavigation.Start.name
    )

    // use appViewModel.getTrackedSymptoms to get a list of symptoms
    // use appViewModel.setTrackedSymptoms to set the symptoms to a new list of symptoms
    Scaffold(
        topBar = {
            SettingAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        NavHost(
            navController = navController,
            startDestination = SettingScreenNavigation.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = SettingScreenNavigation.Start.name) {
                SettingScreenLayout(
                    onNotificationClicked = {
                        navController.navigate(SettingScreenNavigation.Notification.name)
                    },
                    onBackUpClicked = {
                        navController.navigate(SettingScreenNavigation.BackUpAccount.name)
                    },
                    onDeleteClicked = {
                        navController.navigate(SettingScreenNavigation.DeleteAccount.name)
                    },
                )
            }
            composable(route = SettingScreenNavigation.Notification.name) {
                TimeWheel(context= LocalContext.current)
            }
            composable(route = SettingScreenNavigation.BackUpAccount.name) {
                BackUpAccountScreen()
            }
            composable(route = SettingScreenNavigation.DeleteAccount.name) {
                DeleteAccountScreen()
            }
        }
    }
}
