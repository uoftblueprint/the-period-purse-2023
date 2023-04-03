package com.tpp.theperiodpurse.ui.onboarding

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.tpp.theperiodpurse.MainActivity.Companion.RC_SIGN_IN
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.Screen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GoogleSignIn(viewModel: OnboardViewModel, navController: NavHostController, context: Context, googleSignInClient: GoogleSignInClient) {





}