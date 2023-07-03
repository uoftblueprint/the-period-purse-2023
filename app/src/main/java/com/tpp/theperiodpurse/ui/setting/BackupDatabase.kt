package com.tpp.theperiodpurse.ui.setting


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
import com.tpp.theperiodpurse.ui.onboarding.LoadingScreen
import com.tpp.theperiodpurse.ui.viewmodel.OnboardViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BackupDatabase(viewModel: OnboardViewModel,
                   navController: NavHostController,
                   account: Account?,
                   signout: () -> Unit = {},
                   context: Context) {

    val isBackedUp by viewModel.isBackedUp.observeAsState(initial = null)
    val confirmLoad = remember { mutableStateOf(false) }


    LaunchedEffect(Unit){
        if (account != null){
            viewModel.backupDatabase(account = account, context)
        }

    }
    if (isBackedUp == null){
        LoadingScreen()

    }
    else {
        if (isBackedUp == true) {
            LaunchedEffect(Unit){
                viewModel.isBackedUp.postValue(null)
                navController.navigate(SettingScreenNavigation.ConfirmBackup.name)
            }
        } else {
            if (!confirmLoad.value){
                Toast.makeText(context, "ERROR - Please grant all the required permissions", Toast.LENGTH_SHORT).show()
                signout()
                confirmLoad.value = true
                LaunchedEffect(Unit){
                    viewModel.isBackedUp.postValue(null)
                    navController.navigate(SettingScreenNavigation.Start.name)
                }

            }
        }


    }

}
