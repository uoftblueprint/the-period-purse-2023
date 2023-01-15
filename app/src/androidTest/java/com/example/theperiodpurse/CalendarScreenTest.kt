package com.example.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.theperiodpurse.ui.calendar.CalendarScreen
import com.example.theperiodpurse.ui.calendar.CalendarTabItem
import com.example.theperiodpurse.AppViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalendarScreenTest{
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
    fun calendarIsDisplayed() {
        composeTestRule.onNodeWithContentDescription("Calendar Page").assertIsDisplayed()
    }

    // Check scrolling works
    // TODO: Check out espresso for being able to test UI


    // Check if you can go to Cycle Tab
    @Test
    fun appTabs_DisplayCycleTab() {
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()
        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsDisplayed()
    }

    // Check first entry month (12 months before current month)

    // Check last entry month (Current month)

    // Click on date to bring up the tracking menu

}



