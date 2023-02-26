package com.example.theperiodpurse.ui.setting


import android.Manifest
import android.Manifest.permission.SCHEDULE_EXACT_ALARM
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.theperiodpurse.R


@Composable
fun SettingScreenLayout(
    modifier: Modifier = Modifier,
    onNotificationClicked: () -> Unit,
    onBackUpClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
){
    val textModifier = modifier.padding(top = 50.dp, start = 10.dp)
   Column(modifier = modifier
       .fillMaxSize()
       .verticalScroll(rememberScrollState())) {
       Text(
           text = stringResource(R.string.tracking_preferences),
           modifier = textModifier,
           color = Color.Gray
       )

       TrackingPreferencesRow()
       Text(
           text = stringResource(R.string.notifications_heading),
           modifier = textModifier,
           color = Color.Gray
       )
       Row(modifier = modifier.padding(20.dp)) {
           var checked by remember { mutableStateOf(false) }
           Column (modifier = Modifier) {
               Text(text = stringResource(
                   R.string.remind_me_to_log_symptoms),
                   fontWeight = FontWeight.Bold)
               Spacer(modifier = modifier.padding(3.dp))
               Text(text = stringResource(R.string.sample_notification))
           }
           Switch(
               checked = checked,
               onCheckedChange = { checked = !checked},
               modifier = modifier
                   .fillMaxWidth()
                   .wrapContentWidth(Alignment.End),
               colors = SwitchDefaults.colors(
                   uncheckedThumbColor = Color.DarkGray
               )
           )
           val context = LocalContext.current
           var hasAlarmPermission by remember {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                   mutableStateOf(
                       ContextCompat.checkSelfPermission(
                           context,
                           Manifest.permission.POST_NOTIFICATIONS
                       ) == PackageManager.PERMISSION_GRANTED
                   )
               } else mutableStateOf(true)
           }
           val launcher = rememberLauncherForActivityResult(
               contract = ActivityResultContracts.RequestPermission(),
               onResult = { isGranted ->
                   hasAlarmPermission = isGranted
                   if (!isGranted) {
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                           shouldShowRequestPermissionRationale(SCHEDULE_EXACT_ALARM)
                       }
                   }
               }
           )
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
               SideEffect {
                   launcher.launch(SCHEDULE_EXACT_ALARM)
               }
           }

       }
      TabOption(
          stringResource(id = R.string.customize_notifications),
          onTabClicked = onNotificationClicked,
      )
       Text(
           text = stringResource(R.string.account_settings_heading),
           modifier = textModifier,
           color = Color.Gray
       )
       TabOption(
           text = stringResource(R.string.back_up_account),
           onTabClicked = onBackUpClicked
       )
       Spacer(modifier = Modifier.padding(10.dp))
       TabOption(
           text = stringResource(id = R.string.delete_account),
           onTabClicked = onDeleteClicked
       )
       Spacer(modifier = Modifier.padding(10.dp))
       Text(text = stringResource(id = R.string.copyright))
   }
}

@Composable
fun TrackingPreferencesRow(modifier: Modifier = Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.mood),
            icon = painterResource(id = R.drawable.sentiment_neutral_black_24dp),
            contentDescription = stringResource(R.string.mood),
        )
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.exercise),
            icon = painterResource(id = R.drawable.self_improvement_black_24dp),
            contentDescription = stringResource(R.string.exercise)
        )
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.cramps),
            icon = painterResource(id = R.drawable.sick_black_24dp),
            contentDescription = stringResource(R.string.cramps),
        )
        TrackingOptionButton(
            modifier = modifier,
            label = stringResource(R.string.sleep),
            icon = painterResource(id = R.drawable.nightlight_black_24dp),
            contentDescription = stringResource(R.string.sleep)
        )
    }
}

@Composable
fun TrackingOptionButton(modifier: Modifier, label: String, icon: Painter, contentDescription: String) {
    var checked by remember { mutableStateOf(false) }
    val color = if (checked) Color.Green else Color.White
    Column(
        modifier = modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        IconToggleButton(
            checked = checked,
            onCheckedChange = { checked = !checked},
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
fun TabOption(text: String, onTabClicked: () -> Unit){
    TabRow(modifier = Modifier.fillMaxWidth(), selectedTabIndex = 0, backgroundColor = Color.Transparent) {
        Tab(modifier = Modifier.fillMaxWidth(), selected = true, onClick = { onTabClicked() }) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
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

    }
}