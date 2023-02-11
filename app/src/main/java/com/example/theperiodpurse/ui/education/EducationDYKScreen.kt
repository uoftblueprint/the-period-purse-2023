package com.example.theperiodpurse.ui.education

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EducationDYKScreen() {
    EducationBackground()

    Column() {
        Text("Did you know?")
    }
}

@Preview
@Composable
fun PreviewEducationDYKScreen() {
    EducationDYKScreen()
}