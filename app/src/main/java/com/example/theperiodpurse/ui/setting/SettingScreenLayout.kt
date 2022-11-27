package com.example.theperiodpurse.ui.setting


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R


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
               start = 10.dp),
           color = Color.Gray
       )

       TrackingPreferencesRow()
       Text(
           text = stringResource(R.string.notifications_heading),
           modifier = modifier.padding(
               top = 50.dp,
               start = 10.dp),
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
       }
      TabOption(
          stringResource(id = R.string.customize_notifications),
          onTabClicked = onNotificationClicked
      )
       Text(
           text = stringResource(R.string.account_settings_heading),
           modifier = modifier.padding(
               top = 50.dp,
               start = 10.dp),
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
       Text(text = "The Period Purse. All rights reserved.")
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