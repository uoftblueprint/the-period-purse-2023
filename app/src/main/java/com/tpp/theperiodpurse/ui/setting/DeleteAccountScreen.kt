package com.tpp.theperiodpurse.ui.setting

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
    val screenwidth = configuration.screenWidthDp;
    val screenheight = configuration.screenHeightDp

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
                onClick = {
                    navController.navigate(SettingScreenNavigation.ResetDatabase.name)
                },
                colors =  ButtonDefaults.buttonColors(backgroundColor = Color(195, 50, 50))) {
                Text(text = "DELETE ACCOUNT", color = Color.White)

            }
        }
    }

    
}
