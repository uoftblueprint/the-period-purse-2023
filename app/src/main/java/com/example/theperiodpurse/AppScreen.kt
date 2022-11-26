package com.example.theperiodpurse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.theperiodpurse.ui.onboarding.OnboardApp
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                Application()

            }
        }
    }
}

@Composable
fun Application() {
    OnboardApp()
}