package com.tpp.theperiodpurse

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.tpp.theperiodpurse.data.DateDAO
import com.tpp.theperiodpurse.data.DateRepository
import com.tpp.theperiodpurse.data.OnboardUIState
import com.tpp.theperiodpurse.ui.onboarding.OnboardViewModel
import com.tpp.theperiodpurse.ui.setting.SettingScreenNavigation
import com.tpp.theperiodpurse.ui.setting.SettingsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsNavigationTest {
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
            SettingsScreen(outController = navController, navController = navController, context = LocalContext.current, viewModel = null)
        }
    }

    @Test
    fun appNavhost_clickNotifications_navigatesToNotificationsScreen() {
        navController.assertCurrentRouteName(SettingScreenNavigation.Start.name)
        composeTestRule.onNodeWithStringId(R.string.customize_notifications)
            .performClick()
        navController.assertCurrentRouteName(SettingScreenNavigation.Notification.name)
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