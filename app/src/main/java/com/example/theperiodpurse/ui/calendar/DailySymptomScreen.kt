package com.example.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.Screen
import java.time.LocalDate


@Composable
fun DailySymptomScreen(
    date: String="0000-00-00",
    navController: NavController
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val day = LocalDate.parse(date)
        DailySymptomScreenLayout(day, navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailySymptomScreenLayout(date: LocalDate, navController: NavController) {
    Column() {

        Row() {
            IconButton(onClick = {
                navController.navigate(
                    "%s/%s/%s".format(
                            Screen.Calendar.name,
                            Screen.Log.name,
                            date.minusDays(1).toString()
                        )
                ) {
                    popUpTo(Screen.Calendar.name) {
                        inclusive = false
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Log Back Arrow"
                )
            }
            Column() {
                Text(date.toString())
            }
            IconButton(onClick = {
                navController.navigate(
                    "%s/%s/%s".format(
                            Screen.Calendar.name,
                            Screen.Log.name,
                            date.plusDays(1)
                        )
                ) {
                    popUpTo(Screen.Calendar.name) {
                        inclusive = false
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Log Forward Arrow"
                )
            }
       }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DailySymptomScreenLayoutPreview() {
    DailySymptomScreenLayout(
        date = LocalDate.parse("2000-00-00"),
        rememberNavController()
    )
}