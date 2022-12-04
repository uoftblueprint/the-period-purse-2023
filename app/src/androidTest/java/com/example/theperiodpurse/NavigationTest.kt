package com.example.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.theperiodpurse.ui.setting.SettingScreenNavigation
import com.example.theperiodpurse.ui.setting.SettingsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
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
//            ScreenApp(navController = navController)
            SettingsScreen(navController = navController)
        }
    }

    @Test
    fun appNavhost_clickNotifications_navigatesToNotificationsScreen() {
        navController.assertCurrentRouteName(SettingScreenNavigation.Start.name)
        composeTestRule.onNodeWithStringId(R.string.customize_notifications)
            .performClick()
        navController.assertCurrentRouteName(SettingScreenNavigation.Notification.name)
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        navController.assertCurrentRouteName(SettingScreenNavigation.Start.name)
    }

    @Test
    fun appNavhost_clickBackUp_navigatesToBackUpScreen() {
        navController.assertCurrentRouteName(SettingScreenNavigation.Start.name)
        composeTestRule.onNodeWithStringId(R.string.back_up_account)
            .performClick()
        navController.assertCurrentRouteName(SettingScreenNavigation.BackUpAccount.name)
    }

    @Test
    fun appNavhost_clickDelete_navigatesToDeleteScreen() {
        navController.assertCurrentRouteName(SettingScreenNavigation.Start.name)
        composeTestRule.onNodeWithStringId(R.string.delete_account)
            .performClick()
        navController.assertCurrentRouteName(SettingScreenNavigation.DeleteAccount.name)
    }

}