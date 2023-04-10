package com.tpp.theperiodpurse.ui.setting


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
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.tpp.theperiodpurse.AppUiState
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.data.ApplicationRoomDatabase
import com.tpp.theperiodpurse.data.OnboardUIState
import com.tpp.theperiodpurse.ui.calendar.CalendarUIState
import com.tpp.theperiodpurse.ui.onboarding.LoadingScreen
import com.tpp.theperiodpurse.ui.onboarding.OnboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BackupDatabase(viewModel: OnboardViewModel,
                   navController: NavHostController,
                   account: Account?,
                   context: Context) {

    val isBackedUp by viewModel.isBackedUp.observeAsState(initial = null)


    LaunchedEffect(Unit){
        if (account != null){
            viewModel.backupDatabase(account = account, context)
        }

    }
    if (isBackedUp == null){
        LoadingScreen()

    }
    else {
        LaunchedEffect(Unit){

            viewModel.isBackedUp.postValue(null)
            navController.navigate(SettingScreenNavigation.ConfirmBackup.name)

        }

    }

}
