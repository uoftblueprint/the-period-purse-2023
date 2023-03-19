package com.tpp.theperiodpurse

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.ui.component.BottomNavigation
import com.tpp.theperiodpurse.ui.component.FloatingActionButton
import com.tpp.theperiodpurse.ui.onboarding.*
import com.tpp.theperiodpurse.ui.symptomlog.LoggingOptionsPopup
import com.tpp.theperiodpurse.ui.theme.ThePeriodPurseTheme
import java.time.LocalDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                val context = LocalContext.current
                var hasNotificationPermission by remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                context,
                                POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    } else mutableStateOf(true)
                }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        hasNotificationPermission = isGranted
                        if (!isGranted) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)
                            }
                        }
                    }
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    SideEffect {
                        launcher.launch(POST_NOTIFICATIONS)
                    }
                }
                Application(applicationContext)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Application(context: Context) {
    ScreenApp()
    createNotificationChannel(context)
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "notification_channel",
            "Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used for the reminder notifications"

        val notificationManager =
            context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ScreenApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    calendarViewModel: CalendarViewModel = viewModel(),
    skipOnboarding: Boolean = false,
    navController: NavHostController = rememberNavController()
) {
    var loggingOptionsVisible by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            if (currentRoute(navController) in Screen.values().map { it.name }) {
                BottomNavigation(navController = navController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                navController = navController,
                onClickInCalendar = { loggingOptionsVisible = true }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Box {
            NavigationGraph(
                navController = navController,
                startDestination = if (skipOnboarding) Screen.Calendar.name else OnboardingScreen.Welcome.name,
                viewModel = viewModel,
                calendarViewModel = calendarViewModel,
                modifier = modifier.padding(innerPadding)
            )

            if (loggingOptionsVisible) {
                LoggingOptionsPopup(
                    onLogDailySymptomsClick = {
                        navController.navigate(
                            route = "%s/%s/%s"
                                .format(
                                    Screen.Calendar,
                                    Screen.Log,
                                    LocalDate.now().toString()
                                )
                        )
                    },
                    { /* TODO: Go to logging page for multiple dates */ },
                    onExit = { loggingOptionsVisible = false },
                    modifier = modifier.padding(innerPadding)
                )
            }
        }
    }
}
