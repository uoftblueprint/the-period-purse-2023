package com.tpp.theperiodpurse.ui.setting


import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.SCHEDULE_EXACT_ALARM
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.Symptom
import com.tpp.theperiodpurse.ui.education.SocialMedia
import com.tpp.theperiodpurse.ui.education.teal
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.tpp.theperiodpurse.ui.legal.TermsAndPrivacyFooter
import com.tpp.theperiodpurse.AppViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingScreenLayout(
    modifier: Modifier = Modifier,
    outController: NavHostController,
    onNotificationClicked: () -> Unit,
    onBackUpClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    appViewModel: AppViewModel = viewModel(),
    context: Context
){
    val symptoms = appViewModel.getTrackedSymptoms()
    var hasAlarmPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    SCHEDULE_EXACT_ALARM
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasAlarmPermission = isGranted
//                   if (!isGranted) {
//                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
////                           shouldShowRequestPermissionRationale(SCHEDULE_EXACT_ALARM)
//                       }
//                   }
        }
    )

    val hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
//    appViewModel.setAllowReminders(hasNotificationPermission)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        SideEffect {
            launcher.launch(SCHEDULE_EXACT_ALARM)
        }
    }

    val time = appViewModel.getReminderFreq() + " at " + appViewModel.getReminderTime()

    val formatter = DateTimeFormatter.ofPattern("h:mm a") // define the format of the input string
    val formattedTime = appViewModel.getReminderTime()
    val pickedTime = LocalTime.parse(formattedTime, formatter)
    processAlarm(hasNotificationPermission, context, pickedTime, appViewModel, true)

   Column(modifier = modifier
       .fillMaxSize()
       .padding(10.dp)
       .verticalScroll(rememberScrollState())) {
       Text(
           text = stringResource(R.string.tracking_preferences),
           modifier = modifier.padding(top = 30.dp, start = 10.dp),
           color = Color.DarkGray,
           fontWeight = FontWeight.Bold
       )

       TrackingPreferencesRow(symptoms, appViewModel = appViewModel)
       Text(
           text = stringResource(R.string.notifications_heading),
           modifier = modifier.padding(top = 5.dp, start = 10.dp),
           color = Color.DarkGray,
           fontWeight = FontWeight.Bold
       )
       Row(modifier = modifier.padding(20.dp)) {
           Column (modifier = Modifier) {
               Text(text = stringResource(
                   R.string.remind_me_to_log_symptoms),
                   fontWeight = FontWeight.Bold)
               Spacer(modifier = modifier.padding(3.dp))
               Text(text = time,
                   modifier = Modifier.padding(start = 5.dp),
                   color = Color.Gray,
                   fontSize = 15.sp,
               )
           }
           Switch(
               enabled = true,
               checked = appViewModel.getAllowReminders(),
               onCheckedChange = {
                   // ask for notifications permissions again here, if it's currently off in settings
                   if(!hasNotificationPermission){
                       launcher.launch(POST_NOTIFICATIONS)
                   }
                   println(hasNotificationPermission)
                   if(hasNotificationPermission){
                       appViewModel.toggleAllowReminders()
                       processAlarm(hasNotificationPermission, context, pickedTime, appViewModel, false)
                   }
               },
               modifier = modifier
                   .fillMaxWidth()
                   .wrapContentWidth(Alignment.End),
               colors = SwitchDefaults.colors(
                   uncheckedThumbColor = Color.DarkGray
               )
           )
       }
       Divider(modifier = Modifier.padding(start= 10.dp, end = 10.dp))

       NavigateButton(stringResource(id = R.string.customize_notifications),
           onClicked = onNotificationClicked
           )

       Divider(modifier = Modifier.padding(start= 10.dp, end = 10.dp))

       Text(
           text = stringResource(R.string.account_settings_heading),
           modifier = Modifier.padding(start= 10.dp, top = 30.dp),
           color = Color.DarkGray,
           fontWeight = FontWeight.Bold
       )
       NavigateButton( text = stringResource(R.string.back_up_account),
           onClicked = onBackUpClicked
       )
       Divider(modifier = Modifier.padding(start= 10.dp, end = 10.dp))
       NavigateButton(text = stringResource(id = R.string.delete_account),
           onClicked = onDeleteClicked
       )
       Divider(modifier = Modifier.padding(start= 10.dp, end = 10.dp))
       Spacer(modifier = Modifier.padding(50.dp))
       val uriHandler = LocalUriHandler.current
       Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
           SocialMedia(uriHandler)
       }

       Text(modifier = Modifier
           .padding(horizontal = 8.dp, vertical = 12.dp)
           .align(Alignment.CenterHorizontally),
           text = "© 2023 The Period Purse. All rights reserved.",
           textAlign = TextAlign.Center,
           color = Color.DarkGray
       )

       /*
       Terms & Conditions, and Privacy Policy
        */
       Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
           TermsAndPrivacyFooter(outController)
           Spacer(modifier = Modifier.size(80.dp))
       }
   }
}

@RequiresApi(Build.VERSION_CODES.S)
fun processAlarm(hasNotificationsPermission: Boolean, context: Context, pickedTime: LocalTime, appViewModel: AppViewModel, isFirstAlarm: Boolean){
    if(hasNotificationsPermission){
        if(appViewModel.getAllowReminders()){
            setAlarm(context, pickedTime, appViewModel, isFirstAlarm)
            println("alarm set")
        } else {
            cancelAlarm(context, appViewModel)
            println("alarm canceled")
        }
    }

}

@Composable
fun TrackingPreferencesRow(symptoms: List<Symptom>, modifier: Modifier = Modifier, appViewModel: AppViewModel){

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.mood),
            icon = painterResource(id = R.drawable.sentiment_neutral_black_24dp),
            contentDescription = stringResource(R.string.mood),
            ischecked = symptoms.contains(Symptom.MOOD),
            symptom = Symptom.MOOD,
            appViewModel = appViewModel
        )
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.exercise),
            icon = painterResource(id = R.drawable.self_improvement_black_24dp),
            contentDescription = stringResource(R.string.exercise),
            ischecked = symptoms.contains(Symptom.EXERCISE),
            symptom = Symptom.EXERCISE,
            appViewModel = appViewModel
        )
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.cramps),
            icon = painterResource(id = R.drawable.sick_black_24dp),
            contentDescription = stringResource(R.string.cramps),
            ischecked = symptoms.contains(Symptom.CRAMPS),
            symptom = Symptom.CRAMPS,
            appViewModel = appViewModel
        )
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.sleep),
            icon = painterResource(id = R.drawable.nightlight_black_24dp),
            contentDescription = stringResource(R.string.sleep),
            ischecked = symptoms.contains(Symptom.SLEEP),
            symptom = Symptom.SLEEP,
            appViewModel = appViewModel
        )
    }
}

@Composable
fun TrackingOptionButton(modifier: Modifier, label: String, icon: Painter,
                         contentDescription: String, ischecked: Boolean,
                         symptom: Symptom, appViewModel: AppViewModel) {

    val color = if (appViewModel.isSymptomChecked(symptom)) Color(teal) else Color.White
    Column(
        modifier = modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        IconToggleButton(
            checked = ischecked,
            onCheckedChange = {appViewModel.updateSymptoms(symptom)},
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                modifier = Modifier
                    .background(color)
                    .height(50.dp)
                    .width(50.dp)
                    .padding(10.dp)
            )

        }
        Text(modifier = Modifier.padding(5.dp),
            text = label)
    }
}

@Composable
fun NavigateButton(text: String, onClicked: () -> Unit ){
    Button(onClick = onClicked, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
        Color.Transparent), elevation = ButtonDefaults.elevation(0.dp)) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "arrow",
            modifier = Modifier.wrapContentWidth(Alignment.End)
        )

    }
}

/**
 * Preview for Settings Home Page
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingScreenLayout(
        outController = navController,
        onNotificationClicked = {
            navController.navigate(SettingScreenNavigation.Notification.name)
        },
        onBackUpClicked = {
            navController.navigate(SettingScreenNavigation.BackUpAccount.name)
        },
        onDeleteClicked = {
            navController.navigate(SettingScreenNavigation.DeleteAccount.name)
        },
        context = LocalContext.current
    )
}