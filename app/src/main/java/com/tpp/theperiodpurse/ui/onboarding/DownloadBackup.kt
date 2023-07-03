package com.tpp.theperiodpurse.ui.onboarding

import android.accounts.Account
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.ui.viewmodel.OnboardViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DownloadBackup(
    googleAccount: Account?,
    viewModel: OnboardViewModel,
    navHostController: NavHostController,
    context: Context
) {
    val isDownloaded by viewModel.isDownloaded.observeAsState(initial = null)
    val confirmLoad = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (googleAccount != null) {
            viewModel.downloadBackup(googleAccount, context)
        }
    }
    if (isDownloaded == null) {
        LoadingScreen()
    } else {
        if (isDownloaded == true) {
            LaunchedEffect(Unit) {
                viewModel.isDownloaded.postValue(null)
                navHostController.popBackStack(OnboardingScreen.Welcome.name, inclusive = true)
                navHostController.navigate(OnboardingScreen.LoadDatabase.name)
            }
        }
        else if (isDownloaded == false) {
            LaunchedEffect(Unit) {
                viewModel.isDownloaded.postValue(null)
                navHostController.popBackStack(OnboardingScreen.Welcome.name, inclusive = true)
                navHostController.navigate(OnboardingScreen.Welcome.name)
            }
        }

    }
}