package com.tpp.theperiodpurse

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import com.tpp.theperiodpurse.ui.component.BottomNavigation
import com.tpp.theperiodpurse.ui.component.FloatingActionButton
import com.tpp.theperiodpurse.ui.onboarding.*
import com.tpp.theperiodpurse.ui.symptomlog.LoggingOptionsPopup
import com.tpp.theperiodpurse.ui.theme.ThePeriodPurseTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val RC_SIGN_IN = 100
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

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
                Application(context = applicationContext, signIn = { signIn() } )

            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }
    @RequiresApi(Build.VERSION_CODES.S)
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)!!.account


            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
            setContent {
                Application(context = applicationContext, skipDatabase = true, skipWelcome = true, signIn = { signIn() })
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "SignIn Failed", Toast.LENGTH_SHORT).show()
            setContent {
                Application(context = applicationContext, skipDatabase = false, skipWelcome = false, signIn = { signIn() })
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Application(context: Context,
                signIn: () -> Unit,
                skipWelcome: Boolean = false,
                skipDatabase: Boolean = false,
                skipOnboarding: Boolean = false) {
    ScreenApp(signIn = signIn,
        skipOnboarding = skipOnboarding,
        skipWelcome = skipDatabase,
        skipDatabase = skipWelcome,
        context = context)
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
    appViewModel: AppViewModel = viewModel(),
    onboardViewModel: OnboardViewModel = viewModel(),
    calendarViewModel: CalendarViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    signIn: () -> Unit,
    skipWelcome: Boolean = false,
    skipDatabase: Boolean = false,
    skipOnboarding: Boolean = false,
    context: Context,

) {
    appViewModel.loadData(calendarViewModel)
    var loggingOptionsVisible by remember { mutableStateOf(false) }
    var skipOnboarding = skipOnboarding
    val isOnboarded by onboardViewModel.isOnboarded.observeAsState(initial = null)

    if (!skipDatabase){
        LaunchedEffect(Unit) {
            onboardViewModel.checkOnboardedStatus()
        }
    }

    if (isOnboarded == null && !skipDatabase){
        LoadingScreen()
    } else{
        if (!skipDatabase){
            skipOnboarding = (isOnboarded as Boolean)
        }
        Scaffold(
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
                    startDestination = if (skipOnboarding) Screen.Calendar.name else if (skipWelcome) OnboardingScreen.QuestionOne.name else OnboardingScreen.Welcome.name,
                    onboardViewModel = onboardViewModel,
                    appViewModel= appViewModel,
                    calendarViewModel = calendarViewModel,
                    modifier = modifier.padding(innerPadding),
                    signIn = signIn,
                    context = context
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
                        modifier = modifier.padding(bottom = 64.dp)
                    )
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                if (currentRoute(navController) in Screen.values().map { it.name }) {
                    BottomNavigation(navController = navController)
                }
            }
        }
    }
}
