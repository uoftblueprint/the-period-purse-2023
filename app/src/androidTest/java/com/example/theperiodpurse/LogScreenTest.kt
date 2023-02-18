package com.example.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.theperiodpurse.data.LogPrompt
import com.example.theperiodpurse.data.LogSquare
import com.example.theperiodpurse.ui.onboarding.OnboardViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltAndroidTest
class LogScreenTest {
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
            ScreenApp(navController = navController, skipOnboarding = true)
        }
    }

    private fun navigateToLogScreen() {
        composeTestRule
            .onNodeWithContentDescription(LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ).toString()).performClick()
    }

    @Test
    fun appLogScreen_clickOnArrow_changesDate() {
        navigateToLogScreen()
        val initialDate = composeTestRule
            .onNodeWithTag("DateLabel").fetchSemanticsNode()
            .config[SemanticsProperties.Text][0].text
        composeTestRule.onNodeWithContentDescription("Log Back Arrow").performClick()
        val finalDate = composeTestRule
            .onNodeWithTag("DateLabel").fetchSemanticsNode()
            .config[SemanticsProperties.Text][0].text
        assertTrue(initialDate != finalDate)
    }

    @Test
    fun appLogScreen_clickOnFlowTab_opensFlowTab() {
        navigateToLogScreen()
        composeTestRule.onNodeWithStringId(LogPrompt.FLOW.title).performClick()
        composeTestRule.onNodeWithText(LogSquare.FlowHeavy.description).assertIsDisplayed()
    }

    @Test
    fun appLogScreen_clickOnSave_navigatesToCalendar() {
        navigateToLogScreen()
        composeTestRule.onNodeWithStringId(LogPrompt.MOOD.title).performClick()
        composeTestRule
            .onNodeWithContentDescription(LogSquare.MoodHappy.description).performClick()
        composeTestRule
            .onNodeWithContentDescription("Save Button").performClick()
        navController.assertCurrentRouteName(Screen.Calendar.name)
    }

    @Test
    fun appLogScreen_clickOnX_navigatesToCalendar() {
        navigateToLogScreen()
        composeTestRule.onNodeWithContentDescription("Log Close Button").performClick()
        navController.assertCurrentRouteName(Screen.Calendar.name)
    }
}