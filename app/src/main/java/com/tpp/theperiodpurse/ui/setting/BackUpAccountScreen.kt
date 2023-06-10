package com.tpp.theperiodpurse.ui.setting

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.Status
import com.tpp.theperiodpurse.ui.state.OnboardUIState
import com.tpp.theperiodpurse.ui.onboarding.GoogleSignInButton
import com.tpp.theperiodpurse.ui.onboarding.scaledSp

@Composable
fun BackUpAccountScreen(appbar: Unit,
                        navController: NavHostController = rememberNavController(),
                        signIn: () -> Unit,
                        onboardUIState: OnboardUIState?,
                        context: Context
) {
    val configuration = LocalConfiguration.current
    val screenheight = configuration.screenHeightDp

    var confirmBackUp = remember { mutableStateOf(false)  }


    var firstCheck = remember {
        mutableStateOf(true)
    }

    if (firstCheck.value){
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null && onboardUIState?.googleAccount ==null){
            onboardUIState?.googleAccount= account.account
        }
        firstCheck.value = false
    }

    appbar

    if (onboardUIState?.googleAccount != null){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = (screenheight * 0.09).dp,
                    start = (screenheight * 0.03).dp,
                    end = (screenheight * 0.03).dp
                ),
            contentAlignment = Alignment.TopStart
        ) {Column() {
            Text(text= "Back Up Account",
                color = Color.DarkGray,
                fontSize = 18.scaledSp())

            Spacer(modifier = Modifier.height((screenheight * (0.02)).dp))
            Text(text= "Backing up to Google Drive will upload your data to Google Drive and ensure you can access it on other devices.",
                fontSize = 13.scaledSp())
            Spacer(modifier = Modifier.height((screenheight * (0.02)).dp))
            Button(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    confirmBackUp.value = true
                },
                colors =  ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))) {
                Text(text = "Back Up Account", color = Color.White)
            }
            Spacer(modifier = Modifier.height((screenheight * (0.02)).dp))
            Text(text= "Last backup: Not available",
                color = Color.Gray,
                fontSize = 13.scaledSp())
            }
        }
    }
    else {

        val account = GoogleSignIn.getLastSignedInAccount(context)

        if (account != null){
            if (onboardUIState != null) {
                onboardUIState.googleAccount = account.account
                confirmBackUp.value = true
            }
        }
        else {
            val signInResult = remember { mutableStateOf(GoogleSignInResult(GoogleSignInAccount.createDefault(), Status.RESULT_CANCELED)) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = (screenheight * 0.09).dp,
                        start = (screenheight * 0.03).dp,
                        end = (screenheight * 0.03).dp
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Column() {
                    Text(text= "Back Up Account",
                        color = Color.Gray,
                        fontSize = 18.scaledSp())

                    Spacer(modifier = Modifier.height((screenheight * (0.02)).dp))

                    Text(text= "Sign in to backup your data",
                        fontSize = 15.scaledSp())

                    Spacer(modifier = Modifier.height((screenheight * (0.02)).dp))

                    GoogleSignInButton {
                        signIn()
                    }
                    LaunchedEffect(signInResult.value) {
                        if (signInResult.value.isSuccess) {
                            navController.navigate(SettingScreenNavigation.BackUpAccount.name)
                        }
                        else {
                            signInResult.value = GoogleSignInResult(GoogleSignInAccount.createDefault(), Status.RESULT_CANCELED)
                        }
                    }
                }
            }
        }

    }

    if (confirmBackUp.value) {
        confirmBackUp.value = false
        firstCheck.value = true
        navController.navigate(SettingScreenNavigation.BackupDatabase.name)
    }

}