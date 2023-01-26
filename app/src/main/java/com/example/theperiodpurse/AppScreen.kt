package com.example.theperiodpurse

import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.theperiodpurse.ui.onboarding.*
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.example.theperiodpurse.ui.component.BottomNavigation

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                Application(applicationContext)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Application(context: Context) {
    ScreenApp()
    createNotificationChannel(context)
}

fun createNotificationChannel(context: Context) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "notification_channel",
            "Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used for the reminder notifications"

        val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    skipOnboarding: Boolean = false,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            if (currentRoute(navController) in Screen.values().map{ it.name }) {
                BottomNavigation(navController = navController)
            }
        }
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        NavigationGraph(
            navController = navController,
            startDestination = if (skipOnboarding) Screen.Calendar.name else OnboardingScreen.Welcome.name,
            viewModel,
            modifier = modifier.padding(innerPadding)
        )

    }
}
