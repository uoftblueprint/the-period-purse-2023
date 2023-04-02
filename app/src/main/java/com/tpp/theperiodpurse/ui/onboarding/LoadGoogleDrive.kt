package com.tpp.theperiodpurse.ui.onboarding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavHostController
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.services.drive.Drive
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoadGoogleDrive(googleDrive: Drive, viewModel: OnboardViewModel, navHostController: NavHostController) {

    val isDrive by viewModel.isDrive.observeAsState(initial = null)
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            viewModel.checkGoogleDrive(googleDrive)
        }
    }
    if (isDrive == null){
        LoadingScreen()
    }
    else {
        if (isDrive!!.files.isNotEmpty()){
            navHostController.navigate(Screen.Calendar.name)
        }
        else {
            navHostController.navigate(OnboardingScreen.QuestionOne.name)
        }


    }






}