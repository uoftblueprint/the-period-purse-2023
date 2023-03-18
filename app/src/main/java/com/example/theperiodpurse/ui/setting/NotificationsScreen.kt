
package com.example.theperiodpurse.ui.setting


import android.Manifest
import android.content.Context
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
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
import androidx.core.content.ContextCompat
import com.example.theperiodpurse.data.Alarm
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*




class NotificationsScreen : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var context = LocalContext.current
            var hasNotificationPermission by remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                } else mutableStateOf(true)
            }

            TimeWheel(LocalContext.current, hasNotificationPermission)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun TimeWheel(context: Context, hasNotificationsPermission: Boolean){
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

        if(hasNotificationsPermission){
            setAlarm(context, pickedTime)
        }

    }
    ) {
      Text(text = "notification")
    }

}

@RequiresApi(Build.VERSION_CODES.S)
fun setAlarm(context: Context, pickedTime: LocalTime){
    val calendar=Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }
    calendar.apply {
        set(Calendar.HOUR_OF_DAY,pickedTime.hour)
        set(Calendar.MINUTE,pickedTime.minute)
        set(Calendar.SECOND, pickedTime.second)
    }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, Alarm::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    val hasAlarmPermission: Boolean = alarmManager.canScheduleExactAlarms()

    if(hasAlarmPermission){
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}


