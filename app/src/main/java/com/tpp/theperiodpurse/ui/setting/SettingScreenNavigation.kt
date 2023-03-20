package com.tpp.theperiodpurse.ui.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R

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
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen)},
        modifier = modifier.padding(0.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SettingScreenNavigation.valueOf(
        backStackEntry?.destination?.route ?: SettingScreenNavigation.Start.name
    )

    Scaffold(
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        val title = if(currentScreen.title == R.string.settings_home){
            " "
        } else {
            stringResource(id = R.string.customize_notifications)
        }

        SettingAppBar(
            currentScreen = title,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
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
//                TimeWheel(context= LocalContext.current)
                val context = LocalContext.current
                val hasNotificationPermission by remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    } else mutableStateOf(true)
                }
                NotificationsLayout(context= context, hasNotificationPermission)
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
