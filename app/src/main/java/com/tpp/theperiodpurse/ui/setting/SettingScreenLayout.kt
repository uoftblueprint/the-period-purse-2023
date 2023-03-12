package com.tpp.theperiodpurse.ui.setting


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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.education.SocialMedia
import com.tpp.theperiodpurse.ui.education.TermsAndPrivacyFooter
import com.tpp.theperiodpurse.ui.education.teal


@Composable
fun SettingScreenLayout(
    modifier: Modifier = Modifier,
    onNotificationClicked: () -> Unit,
    onBackUpClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
){
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

       TrackingPreferencesRow()
       Text(
           text = stringResource(R.string.notifications_heading),
           modifier = modifier.padding(top = 5.dp, start = 10.dp),
           color = Color.DarkGray,
           fontWeight = FontWeight.Bold
       )
       Row(modifier = modifier.padding(20.dp)) {
           var checked by remember { mutableStateOf(false) }
           Column (modifier = Modifier) {
               Text(text = stringResource(
                   R.string.remind_me_to_log_symptoms),
                   fontWeight = FontWeight.Bold)
               Spacer(modifier = modifier.padding(3.dp))
               Text(text = stringResource(R.string.sample_notification),
                   modifier = Modifier.padding(start = 5.dp),
                   color = Color.Gray,
                   fontSize = 15.sp,
               )
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
       Spacer(modifier = Modifier.padding(20.dp))
       val uriHandler = LocalUriHandler.current
       val navController = rememberNavController()
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
           TermsAndPrivacyFooter(navController)
           Spacer(modifier = Modifier.size(80.dp))
       }
   }
}

@Composable
fun TrackingPreferencesRow(modifier: Modifier = Modifier){
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
    val color = if (checked) Color(teal) else Color.White
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
@Preview
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingScreenLayout(
        onNotificationClicked = {
            navController.navigate(SettingScreenNavigation.Notification.name)
        },
        onBackUpClicked = {
            navController.navigate(SettingScreenNavigation.BackUpAccount.name)
        },
        onDeleteClicked = {
            navController.navigate(SettingScreenNavigation.DeleteAccount.name)
        },
    )
}