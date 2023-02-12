package com.example.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.theperiodpurse.ui.calendar.CalendarTabItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
    fun appCalendarScreen_clickOnArrow_showAndHideSymptomOptions() {
        composeTestRule.onNodeWithContentDescription("Expand to switch symptoms")
            .performClick()
        composeTestRule.onNodeWithContentDescription("Mood")
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Cramps")
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithTag("Selected Symptom")
        hasContentDescription("Cramps")
    }

    @Test
    fun calendarIsDisplayed() {
        composeTestRule.onNodeWithContentDescription("Calendar Page").assertIsDisplayed()
    }

    // Check last entry month (Current month)
    @Test
    fun displayCurrentMonth() {
        composeTestRule.onNodeWithContentDescription(
            LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
        ).assertIsDisplayed()
    }

    // Check scrolling works
    @Test
    fun scrollTo_ActionAssertion() {
        composeTestRule.onNodeWithContentDescription("Calendar")
            .assert(hasScrollAction())
    }

    // Check if you can go to Cycle Tab
    @Test
    fun appTabs_DisplayCycleTab() {
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()
        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsDisplayed()
    }

    // Click on date to bring up the tracking menu
    @Test
    fun appScreen_DisplayLogScreen() {
        composeTestRule.onNodeWithContentDescription(
            LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ).toString()
        ).performClick()
    }

    @Test
    fun calendarScreen_OpenLoggingOptionsOverlay() {
        composeTestRule.onNodeWithContentDescription("Open logging options").performClick()
        composeTestRule.onNodeWithContentDescription("Log daily symptoms").performClick()
        var date = composeTestRule
            .onNodeWithTag("DateLabel").fetchSemanticsNode()
            .config[SemanticsProperties.Text][0].text
        var today = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        assert(date == today)
    }
}