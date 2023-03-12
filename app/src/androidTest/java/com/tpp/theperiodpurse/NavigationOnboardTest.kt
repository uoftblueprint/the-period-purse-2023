package com.example.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.theperiodpurse.ui.onboarding.OnboardViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

@HiltAndroidTest
class NavigationOnboardTest {
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
            ScreenApp(navController = navController, skipOnboarding = false, viewModel =
            onboardViewModel) { signIn() }
        }
    }

    @Test
    fun appNavHost_Onboard_navigatesToSummary_viewModel() {

        // welcome page
        navController.assertCurrentRouteName(OnboardingScreen.Welcome.name)


        // next to quesiton one page
        composeTestRule.onNodeWithContentDescription("Next").performClick()
            .performClick()
        navController.assertCurrentRouteName(OnboardingScreen.QuestionOne.name)

        // text field input test and next to question two page
        val textFieldValue = "5"
        composeTestRule.onNodeWithContentDescription("Pick Days")
            .performTextInput(textFieldValue)
        composeTestRule.onNodeWithContentDescription("Pick Days")
            .performImeAction()
        composeTestRule.onNodeWithContentDescription("Next").performClick()


        navController.assertCurrentRouteName(OnboardingScreen.QuestionTwo.name)

        composeTestRule.onNodeWithContentDescription("Skip").performClick()

        // calendar input and next to question 3 page

//        composeTestRule.onNodeWithContentDescription("datepick").performClick()
//
//        // Click on the "OK" button to close the dialog
//        composeTestRule.onNodeWithText("cancel").performClick()
//        composeTestRule.onNodeWithContentDescription("Next").performClick()

        navController.assertCurrentRouteName(OnboardingScreen.QuestionThree.name)

        composeTestRule.onNodeWithContentDescription("Mood").performClick()

        composeTestRule.onNodeWithContentDescription("fitness").performClick()
////
////
        composeTestRule.onNodeWithContentDescription("Cramps").performClick()
//
        composeTestRule.onNodeWithContentDescription("Sleep").performClick()
//

//        composeTestRule.onNodeWithContentDescription("Next").performClick()
        composeTestRule.onNodeWithContentDescription("Skip").performClick()
        navController.assertCurrentRouteName(OnboardingScreen.Summary.name)
        composeTestRule.onNodeWithText("5 days").assertExists()
        composeTestRule.onNodeWithContentDescription("Mood").assertExists()
        composeTestRule.onNodeWithContentDescription("Cramps").assertExists()
        composeTestRule.onNodeWithContentDescription("Sleep").assertExists()
        composeTestRule.onNodeWithContentDescription("Exercise").assertExists()
    }
    @Test
    fun appNavHost_clickOnboard_navigatesToSummaryBack() {
        // welcome page
        navController.assertCurrentRouteName(OnboardingScreen.Welcome.name)

        // next to quesiton one page
        composeTestRule.onNodeWithContentDescription("Next").performClick()

        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(OnboardingScreen.QuestionOne.name)
        }

        // next to quesiton two page
        composeTestRule.onNodeWithContentDescription("Skip").performClick()

        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(OnboardingScreen.QuestionTwo.name)
        }
//

        composeTestRule.onNodeWithContentDescription("Skip").performClick()

//

        navController.assertCurrentRouteName(OnboardingScreen.QuestionThree.name)
        composeTestRule.onNodeWithContentDescription("Skip").performClick()
        navController.assertCurrentRouteName(OnboardingScreen.Summary.name)
        // back to quesiton three page
        composeTestRule.onNodeWithContentDescription("Back").performClick()


        navController.assertCurrentRouteName(OnboardingScreen.QuestionThree.name)
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // back to quesiton two page
        navController.assertCurrentRouteName(OnboardingScreen.QuestionTwo.name)

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // back to quesiton one page
        navController.assertCurrentRouteName(OnboardingScreen.QuestionOne.name)

        composeTestRule.onNodeWithContentDescription("Back").performClick()


        // back to welcome page
        navController.assertCurrentRouteName(OnboardingScreen.Welcome.name)



    }


    fun signIn(){

    }

}



