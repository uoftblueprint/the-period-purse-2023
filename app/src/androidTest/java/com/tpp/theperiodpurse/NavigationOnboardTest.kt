package com.tpp.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.tpp.theperiodpurse.data.*
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import com.tpp.theperiodpurse.ui.onboarding.OnboardViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import javax.inject.Inject

@HiltAndroidTest
class NavigationOnboardTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Inject
    lateinit var appViewModel: AppViewModel

    @Inject
    lateinit var onboardViewModel: OnboardViewModel

    @Inject
    lateinit var calendarViewModel: CalendarViewModel

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var dateRepository: DateRepository

    private val symptomList = arrayListOf(
        Symptom.EXERCISE, Symptom.SLEEP, Symptom.MOOD,
        Symptom.CRAMPS
    );
    private val currentDate = java.util.Date()
    private val duration: Duration = Duration.ofHours(1)
    private val dateList = arrayListOf(
        Date(
            date = currentDate,
            flow = FlowSeverity.Heavy,
            mood = Mood.ANGRY,
            exerciseLength = duration,
            exerciseType = Exercise.YOGA,
            crampSeverity = CrampSeverity.Bad,
            sleep = duration,
            notes = ""
        )
    )

    private fun insertDate() {
        runBlocking {
            dateRepository.addDate(dateList[0])
        }
    }

    private fun insertUser() {
        val user = User(
            symptomsToTrack = symptomList,
            periodHistory = dateList,
            averagePeriodLength = 0,
            averageCycleLength = 0,
            daysSinceLastPeriod = 0
        )
        runBlocking {
            userRepository.addUser(user)
        }
    }

    @get:Rule
    // Used to manage the components' state and is used to perform injection on tests
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setupNavHost() {
        hiltRule.inject()
        insertDate()
        insertUser()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            ScreenApp(
                navController = navController,
                skipOnboarding = false,
                skipWelcome = false,
                onboardViewModel = onboardViewModel,
                appViewModel = appViewModel,
                calendarViewModel = calendarViewModel
            ) { signIn() }
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
    @Test
    fun appNavHost_blank_input_questionone() {
        // check if no crash
        navController.assertCurrentRouteName(OnboardingScreen.Welcome.name)

        // next to quesiton one page
        composeTestRule.onNodeWithContentDescription("Next").performClick()

        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(OnboardingScreen.QuestionOne.name)
        }
        composeTestRule.onNodeWithContentDescription("Pick Days")
            .performImeAction()

        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(OnboardingScreen.QuestionOne.name)
        }
    }




    fun signIn(){

    }

}


