package com.tpp.theperiodpurse.ui.setting

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.Screen
import com.tpp.theperiodpurse.data.ApplicationRoomDatabase
import com.tpp.theperiodpurse.ui.onboarding.LoadingScreen
import com.tpp.theperiodpurse.ui.onboarding.OnboardViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeleteAccountScreen(
    appBar: Unit,
    navController: NavHostController = rememberNavController(),
) {
    val configuration = LocalConfiguration.current
    val screenheight = configuration.screenHeightDp

    var confirmDelete = remember { mutableStateOf(false)  }

    appBar
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = (screenheight * 0.09).dp,
                start = (screenheight * 0.03).dp,
                end = (screenheight * 0.03).dp
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column() {
            Text(text = "Are you sure you want to delete your account? You cannot undo this action.",
            fontSize = 15.sp)
            Button(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    confirmDelete.value = true
                },
                colors =  ButtonDefaults.buttonColors(backgroundColor = Color(195, 50, 50))) {
                Text(text = "Delete Account", color = Color.White)
            }
            if (confirmDelete.value) {

                    AlertDialog(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = Color.White,
                        contentColor = Color.Black,
                        onDismissRequest = { confirmDelete.value = false },
                        title = {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.Warning,
                                    contentDescription = "Warning Icon",
                                    tint = Color(195, 50, 50),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Delete Account",
                                    style = MaterialTheme.typography.h6
                                )
                            }
                        },
                        text = {
                            Text(
                                text = "Are you sure you want to delete your account?",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Center
                            )
                        },
                        confirmButton = {
                            OutlinedButton(
                                onClick = {
                                    confirmDelete.value = false
                                    navController.navigate(SettingScreenNavigation.ResetDatabase.name)
                                },
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f)
                                    .height(48.dp)
                                    .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                            ) {
                                Text(
                                    text = "Delete",
                                    style = MaterialTheme.typography.button,
                                    color = Color(195, 50, 50)
                                )
                            }
                        },
                        dismissButton = {
                            OutlinedButton(
                                onClick = { confirmDelete.value = false },
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f)
                                    .height(48.dp)
                                    .fillMaxWidth(),

                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                            ) {
                                Text(
                                    text = "Cancel",
                                    style = MaterialTheme.typography.button,
                                    color = Color.Blue
                                )
                            }
                        }
                    )

                }



            }

    }

}


