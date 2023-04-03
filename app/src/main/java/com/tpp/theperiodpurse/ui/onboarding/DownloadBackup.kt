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
import com.google.api.services.drive.Drive
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DownloadBackup(googleAccount: Account?, viewModel: OnboardViewModel, navHostController: NavHostController, context: Context) {

    val isDownloaded by viewModel.isDownloaded.observeAsState(initial = null)


    if (isDownloaded == null){
        LoadingScreen()
    }
    else {

    }



}