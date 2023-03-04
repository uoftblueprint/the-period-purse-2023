package com.example.theperiodpurse.data

data class OnboardUIState(/** Selected days quantity  */
                          val days: Int = 0,
                          /** Selected date for pickup (such as "Jan 1") */
                          val date: String = "",
                          /** Available Symptoms dates for the order*/
                          val symptomsOptions: List<String> = listOf(),
                          /** Available dates for the track*/
                          val dateOptions: List<String> = listOf(),
                          val user: User? = null,

                          val onboarded: Boolean = false
)
