package com.tpp.theperiodpurse.ui.onboarding

import android.accounts.Account
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.ui.viewmodel.OnboardViewModel
import android.widget.Toast

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoadGoogleDrive(
    googleAccount: Account?,
    viewModel: OnboardViewModel,
    navHostController: NavHostController,
    signout: () -> Unit = {},
    context: Context
) {
    val isDrive by viewModel.isDrive.observeAsState(initial = null)
    val isDriveSafe by viewModel.isDriveSafe.observeAsState(initial = null)
    val confirmLoad = remember { mutableStateOf(false) }
    val decision = remember { mutableStateOf(false) }
    if (googleAccount == null) {
        navHostController.navigate(OnboardingScreen.QuestionOne.name)
    }
    LaunchedEffect(Unit) {
        if (googleAccount != null) {
            viewModel.checkGoogleDrive(account = googleAccount, context = context)
        }
    }
    if (isDrive == null && isDriveSafe == null) {
        LoadingScreen()
    } else {
        if (isDrive != null) {
            if (!confirmLoad.value) {
                AlertDialog(modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    onDismissRequest = { },
                    title = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Use Backup",
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    text = {
                        Text(
                            text = "Would you like to use your backed up data for this app?",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center
                        )
                    },
                    confirmButton = {
                        OutlinedButton(
                            onClick = {
                                confirmLoad.value = true
                                decision.value = true
                            },
                            modifier = Modifier
                                .padding(2.dp)
                                .height(48.dp)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "Yes",
                                style = MaterialTheme.typography.button,
                                color = Color.Blue
                            )
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { confirmLoad.value = true },
                            modifier = Modifier
                                .padding(2.dp)
                                .height(48.dp)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "No",
                                style = MaterialTheme.typography.button,
                                color = Color(195, 50, 50),
                            )
                        }
                    })
            }
            if (isDrive!!.files.isNotEmpty() && confirmLoad.value && decision.value) {
                confirmLoad.value = false
                LaunchedEffect(Unit) {
                    navHostController.navigate(OnboardingScreen.DownloadBackup.name)
                }
            } else if (confirmLoad.value) {
                confirmLoad.value = false
                LaunchedEffect(Unit) {
                    navHostController.navigate(OnboardingScreen.QuestionOne.name)
                }
            }
        } else if (isDriveSafe != null){
            Toast.makeText(context, "ERROR - Please grant all the required permissions", Toast.LENGTH_SHORT).show()
            signout()
            if (!confirmLoad.value) {
                LaunchedEffect(Unit) {
                    navHostController.popBackStack(OnboardingScreen.Welcome.name, inclusive = true)
                    navHostController.navigate(OnboardingScreen.Welcome.name)
                }
                confirmLoad.value = true
            }
        }
    }
}