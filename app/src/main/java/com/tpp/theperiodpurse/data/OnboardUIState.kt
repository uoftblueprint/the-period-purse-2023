package com.tpp.theperiodpurse.data

import android.accounts.Account

data class OnboardUIState(/** Selected days quantity  */
                          var days: Int = 0,
                          /** Selected date for pickup (such as "Jan 1") */
                          var date: String = "",
                          /** Available Symptoms dates for the order*/
                          var symptomsOptions: List<String> = listOf(),
                          /** Available dates for the track*/
                          val dateOptions: List<String> = listOf(),
                          val user: User? = null,
                          var googleAccount: Account? = null
)
