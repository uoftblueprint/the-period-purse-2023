package com.example.theperiodpurse.ui.setting

import android.os.Build
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.Application
import com.example.theperiodpurse.GoogleSignInButton
import com.example.theperiodpurse.GoogleSignOutButton
import com.example.theperiodpurse.MainActivity
import com.example.theperiodpurse.ui.onboarding.QuickStartButton

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingPage(mainActivity: MainActivity){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {



        // Sign in with Google Button
        GoogleSignOutButton {
            mainActivity.signOut()
        }




    }

}
