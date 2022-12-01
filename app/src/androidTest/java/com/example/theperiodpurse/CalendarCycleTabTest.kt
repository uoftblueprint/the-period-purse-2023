package com.example.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.theperiodpurse.ui.calendar.CalendarTabItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalendarCycleTabTest {
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
            ScreenApp(navController = navController)
        }
    }


    private fun navigateToCalendarScreen() {
        composeTestRule.onNodeWithContentDescription("Navigate to Calendar page")
            .performClick()
    }

    private fun navigateToCycleScreen() {
        navigateToCalendarScreen()
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()
    }

    @Test
    fun appTabs_clickCalendar_CycleNotDisplayed() {
        navigateToCalendarScreen()

        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsNotDisplayed()
    }

    @Test
    fun appTabs_clickCalendar_CalendarDisplayed() {
        // update when Calendar screen is implemented with text from now screen
        navigateToCalendarScreen()

        composeTestRule.onNodeWithContentDescription("Calendar Page").assertIsDisplayed()
    }

    @Test
    fun appTabs_clickCalendar_CalendarSelected() {
        navigateToCalendarScreen()

        composeTestRule.onNodeWithText(CalendarTabItem.CalendarTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_clickCycleTab_DisplaysCycle() {
        navigateToCalendarScreen()
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()

        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsDisplayed()
    }

    @Test
    fun appTabs_clickCycleTab_SelectsCycle() {
        navigateToCalendarScreen()
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()

        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_swipeCalendarPage_DisplaysCycle() {
        navigateToCalendarScreen()
        composeTestRule.onRoot().performTouchInput {
            swipeLeft()
        }

        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsDisplayed()
    }

    @Test
    fun appTabs_swipeCalendarPage_SelectsCycle() {
        navigateToCalendarScreen()
        composeTestRule.onRoot().performTouchInput {
            swipeLeft()
        }

        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_swipeCyclePage_SelectsCalendar() {
        navigateToCalendarScreen()
        composeTestRule.onRoot().performTouchInput {
            swipeLeft()
            swipeRight()
        }

        composeTestRule.onNodeWithText(CalendarTabItem.CalendarTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_swipeCyclePage_DisplaysCalendar() {
        navigateToCalendarScreen()
        composeTestRule.onRoot().performTouchInput {
            swipeLeft()
            swipeRight()
        }

        composeTestRule.onNodeWithContentDescription("Calendar Page").assertIsDisplayed()
    }

    @Test
    fun appTabs_clickCalendarTab_SelectsCalendar() {
        navigateToCycleScreen()
        composeTestRule.onNodeWithText(CalendarTabItem.CalendarTab.title).performClick()

        composeTestRule.onNodeWithText(CalendarTabItem.CalendarTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_clickCalendarTab_DisplaysCalendar() {
        navigateToCycleScreen()
        composeTestRule.onNodeWithText(CalendarTabItem.CalendarTab.title).performClick()

        composeTestRule.onNodeWithContentDescription("Calendar Page").assertIsDisplayed()
    }

}