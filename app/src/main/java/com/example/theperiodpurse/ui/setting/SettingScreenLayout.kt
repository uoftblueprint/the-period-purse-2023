package com.example.theperiodpurse.ui.setting

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme

//class SettingScreen {
//}

@Composable
fun SettingScreenLayout(
    modifier: Modifier = Modifier,
    onNotificationClicked: () -> Unit,
    onBackUpClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
){
   Column(modifier = modifier
       .fillMaxSize()
       .verticalScroll(rememberScrollState())) {
       Text(
           text = stringResource(R.string.tracking_preferences),
           modifier = modifier.padding(
               top = 50.dp,
               start = 10.dp))
       Row(modifier = modifier
           .fillMaxWidth()
           .padding(30.dp),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Center) {
           TrackingOptionButton(
               modifier = modifier,
               label = stringResource(R.string.mood),
               icon = painterResource(id = R.drawable.sentiment_neutral_black_24dp)
           )
           TrackingOptionButton(
               modifier = modifier,
               label = stringResource(R.string.exercise),
               icon = painterResource(id = R.drawable.self_improvement_black_24dp)
           )
           TrackingOptionButton(
               modifier = modifier,
               label = stringResource(R.string.cramps),
               icon = painterResource(id = R.drawable.sick_black_24dp)
           )
           TrackingOptionButton(
               modifier = modifier,
               label = stringResource(R.string.sleep),
               icon = painterResource(id = R.drawable.nightlight_black_24dp)
           )


       }

       Text(
           text = stringResource(R.string.notifications_heading),
           modifier = modifier.padding(
               top = 50.dp,
               start = 10.dp)
       )
       Row(modifier = modifier.padding(20.dp)) {
           Text(text = stringResource(R.string.remind_me_to_log_symptoms))
           Switch(
               checked = false,
               onCheckedChange = { /* TODO: change*/},
               modifier = modifier
                   .fillMaxWidth()
                   .wrapContentWidth(Alignment.End),
               colors = SwitchDefaults.colors(
                   uncheckedThumbColor = Color.DarkGray
               )
           )
       }
       Divider(thickness = 1.dp)

      TabOption(
          stringResource(id = R.string.customize_notifications),
          onTabClicked = onNotificationClicked
      )

       Divider(thickness = 1.dp)
       Text(
           text = stringResource(R.string.account_settings_heading),
           modifier = modifier.padding(
               top = 50.dp,
               start = 10.dp)
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
   }

}

@Composable
fun TrackingOptionButton(modifier: Modifier, label: String, icon: Painter) {
    var checked by remember { mutableStateOf(false) }
    val color = if (checked) Color.Green else Color.White
    Column(
        modifier = modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        IconToggleButton(checked = checked,
            onCheckedChange = { checked = !checked }
        ) {
            Icon(
                painter = icon,
                contentDescription = "contentDescription",
                modifier = Modifier
                    .background(color)
                    .height(50.dp)
                    .width(50.dp)
            )

        }
        Text(modifier = Modifier.padding(5.dp),
            text = label)
    }
}

@Composable
fun TabOption(text: String, onTabClicked: () -> Unit){
    TabRow(modifier = Modifier.fillMaxWidth(), selectedTabIndex = 0, backgroundColor = Color.White) {
        Tab(modifier = Modifier.fillMaxWidth(), selected = true, onClick = { onTabClicked() }) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = text)
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "arrow",
                    modifier = Modifier.wrapContentWidth(Alignment.End)
                )
            }
        }

    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun SettingScreenScreenPreview() {
//    ThePeriodPurseTheme {
//        SettingScreenLayout()
//    }
//}