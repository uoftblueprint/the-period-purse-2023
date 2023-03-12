package com.tpp.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.tpp.theperiodpurse.data.DateRepository
import com.tpp.theperiodpurse.data.UserRepository
import com.tpp.theperiodpurse.ui.calendar.CalendarTabItem
import com.tpp.theperiodpurse.ui.onboarding.OnboardViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CalendarCycleTabTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Inject
    lateinit var onboardViewModel: OnboardViewModel

    @get:Rule
    // Used to manage the components' state and is used to perform injection on tests
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setupNavHost() {
        hiltRule.inject()
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            ScreenApp(navController = navController, skipOnboarding = true, viewModel =
            onboardViewModel) { signIn() }
        }
    }


    private fun navigateToCycleScreen() {
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()
    }

    @Test
    fun appTabs_clickCalendar_CycleNotDisplayed() {
        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsNotDisplayed()
    }

    @Test
    fun appTabs_clickCalendar_CalendarDisplayed() {
        composeTestRule.onNodeWithContentDescription("Calendar Page").assertIsDisplayed()
    }

    @Test
    fun appTabs_clickCalendar_CalendarSelected() {
        composeTestRule.onNodeWithText(CalendarTabItem.CalendarTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_clickCycleTab_DisplaysCycle() {
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()

        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsDisplayed()
    }

    @Test
    fun appTabs_clickCycleTab_SelectsCycle() {
        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).performClick()

        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_swipeCalendarPage_DisplaysCycle() {
        composeTestRule.onRoot().performTouchInput {
            swipeLeft()
        }

        composeTestRule.onNodeWithContentDescription("Cycle Page").assertIsDisplayed()
    }

    @Test
    fun appTabs_swipeCalendarPage_SelectsCycle() {
        composeTestRule.onRoot().performTouchInput {
            swipeLeft()
        }

        composeTestRule.onNodeWithText(CalendarTabItem.CycleTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_swipeCyclePage_SelectsCalendar() {
        composeTestRule.onRoot().performTouchInput {
            swipeLeft()
            swipeRight()
        }

        composeTestRule.onNodeWithText(CalendarTabItem.CalendarTab.title).assertIsSelected()
    }

    @Test
    fun appTabs_swipeCyclePage_DisplaysCalendar() {
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
    fun signIn(){

    }

}