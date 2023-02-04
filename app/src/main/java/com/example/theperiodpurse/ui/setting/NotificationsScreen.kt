package com.example.theperiodpurse.ui.setting


import android.content.Context
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.data.Alarm
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*




class NotificationsScreen : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeWheel(LocalContext.current)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeWheel(context: Context){
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {
            timeDialogState.show()
        }) {
            Text(text = "Pick time")
        }
        Text(text = formattedTime)
        Spacer(modifier = Modifier.padding(10.dp))
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(
                    context,
                    "Clicked ok",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a time",
        ) {
            pickedTime = it
        }
    }
    Button(onClick = {
        setAlarm(context)
    }
    ) {
      Text(text = "notification")
    }

}

fun setAlarm(context: Context){
    val time = System.currentTimeMillis() + 2000
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, Alarm::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
}



