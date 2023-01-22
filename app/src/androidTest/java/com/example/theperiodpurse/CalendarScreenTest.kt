package com.example.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalendarScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            ScreenApp(navController = navController, skipOnboarding = true)
        }
    }

    @Test
    fun appLogScreen_clickOnArrow_showAndHideSymptomOptions() {
        composeTestRule.onNodeWithContentDescription("Expand to switch symptoms")
            .performClick()
        composeTestRule.onNodeWithTag("Symptom Options", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Expand to switch symptoms")
            .performClick()
        composeTestRule.onNodeWithTag("Symptom Options", useUnmergedTree = true)
            .assertIsNotDisplayed()
    }
}