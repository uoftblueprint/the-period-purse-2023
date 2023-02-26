package com.example.theperiodpurse

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.theperiodpurse.ui.onboarding.*
import com.example.theperiodpurse.ui.component.BottomNavigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser


class MainActivity : ComponentActivity() {

    companion object {

        const val RC_SIGN_IN = 100
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            if (mAuth.currentUser == null) {
                LoginScreen()
            } else {
                val user: FirebaseUser = mAuth.currentUser!!
                Application(true, this)


            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // result returned from launching the intent from GoogleSignInApi.getSignInIntent()
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google SignIn was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: Exception) {
                    // Google SignIn Failed
                    Log.d("SignIn", "Google SignIn Failed")
                }
            } else {
                Log.d("SignIn", exception.toString())
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // SignIn Successful
                    Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
                    setContent {
                        Application(mainActivity = this)
                    }
                } else {
                    // SignIn Failed
                    Toast.makeText(this, "SignIn Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun signOut() {
        // get the google account
        val googleSignInClient: GoogleSignInClient

        // configure Google SignIn
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Sign Out of all accounts
        mAuth.signOut()
        googleSignInClient.signOut()
            .addOnSuccessListener {
                Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_SHORT).show()
                setContent {
                    LoginScreen()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sign Out Failed", Toast.LENGTH_SHORT).show()
                setContent {
                    LoginScreen()
                }
            }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun LoginScreen() {

        val configuration = LocalConfiguration.current

        val screenwidth = configuration.screenWidthDp;

        val screenheight = configuration.screenHeightDp;



        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )




        Column(
            modifier = Modifier
                .padding((screenheight*0.02).dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            Spacer(modifier = Modifier.height((screenheight*0.09).dp))

            // Logo Image
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier.size((screenheight*0.25).dp)
            )
            Spacer(modifier = Modifier.height((screenheight*0.09).dp))

            // Welcome text
            Text(text = stringResource(R.string.welcome), style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height((screenheight*0.13).dp))

            // Quick Start button
            QuickStartButton(
                onClick = {setContent {
                    Application(mainActivity = MainActivity())
                }}
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Sign in with Google Button
            GoogleSignInButton {
                signIn()
            }
            Spacer(modifier = Modifier.height((screenheight*0.006).dp))

            val annotatedLinkString = buildAnnotatedString {
                val str = "Terms and Conditions and Privacy Policy"
                var startIndex = str.indexOf("Terms and Conditions")
                var endIndex = startIndex + 20
                addStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    ), start = startIndex, end = endIndex
                )
                startIndex = str.indexOf("Privacy Policy")
                endIndex = startIndex + 14
                addStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Bold
                    ), start = startIndex, end = endIndex
                )
                append(str)
            }

            Text("By continuing, you accept the", textAlign = TextAlign.Center, )
            Text(text = annotatedLinkString, textAlign = TextAlign.Center)
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleSignInButton(
    signInClicked: () -> Unit
) {

    Surface(onClick = signInClicked,
            modifier = Modifier.widthIn(min = 350.dp).height(50.dp),
            shape= RoundedCornerShape(15),
            border = BorderStroke(width = 1.dp, color=Color.LightGray),
            color = MaterialTheme.colors.surface
    ) {
        Row (modifier = Modifier
            .padding(
                start = 12.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Button",
                tint=Color.Unspecified,

            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Sign in with Google",
                color = Color.Black, fontSize = 20.sp,
            )

        }

    }

}

@ExperimentalMaterialApi
@Composable
@Preview
private fun GoogleButtonPreview(){
    GoogleSignInButton {

    }
}


@Composable
fun GoogleSignOutButton(
    signOutClicked: () -> Unit
) {

    Button(onClick = signOutClicked) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.CenterVertically),
            text = "Sign Out Of Google",
            fontSize = MaterialTheme.typography.h6.fontSize,
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Application(skipOnboarding: Boolean = false, mainActivity: MainActivity) {
    ScreenApp(skipOnboarding = skipOnboarding, mainActivity = mainActivity)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenApp(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = viewModel(),
    skipOnboarding: Boolean = false,
    navController: NavHostController = rememberNavController(),
    mainActivity: MainActivity
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
            startDestination = if (skipOnboarding) Screen.Calendar.name else OnboardingScreen.QuestionOne.name,
            viewModel,
            modifier = modifier.padding(innerPadding),
            mainActivity = mainActivity,
        )

    }
}




