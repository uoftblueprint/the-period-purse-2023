package com.tpp.theperiodpurse.ui.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.ui.onboarding.GoogleSignInButton
import kotlin.math.sign

@Composable
fun BackUpAccountScreen(appbar: Unit,
                        navController: NavHostController = rememberNavController(),
                        signIn: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenheight = configuration.screenHeightDp
    appbar
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
                fontSize = 18.sp)

            Spacer(modifier = Modifier.height((screenheight * (0.02)).dp))

            Text(text= "Sign in to backup your data",
                fontSize = 15.sp)

            Spacer(modifier = Modifier.height((screenheight * (0.02)).dp))

            GoogleSignInButton {
                signIn()
            }


        }
    }


}