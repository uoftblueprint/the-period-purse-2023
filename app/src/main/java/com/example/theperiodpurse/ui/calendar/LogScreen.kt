package com.example.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.Screen
import java.time.LocalDate


@Composable
fun LogScreen(
    date: String="0000-00-00",
    navController: NavController
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val day = LocalDate.parse(date)
        LogScreenLayout(day, navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreenLayout(date: LocalDate, navController: NavController) {
    Column() {
        LogScreenTopBar(
            navController = navController,
            date = date
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreenTopBar(navController: NavController, date: LocalDate) {
    Box() {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Log Close Button"
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .height(50.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.33f)
            ) {
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
                    } },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Log Back Arrow"
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.33f)
            ) {
                Text(date.toString())
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.33f)
            ) {
                IconButton(onClick = {
                    navController.navigate("%s/%s/%s"
                        .format(
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
}

@Preview
@Composable
fun LogScreenTopBarPreview() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LogScreenTopBar(
            date = LocalDate.parse("2000-01-01"),
            navController = rememberNavController()
        )
    }
}