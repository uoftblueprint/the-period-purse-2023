package com.example.theperiodpurse.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun DailySymptomScreen(date: String="0000-00-00") {
    Column() {
        Text(text = date)
    }
}

@Composable
fun DailySymptomScreenLayout() {

}