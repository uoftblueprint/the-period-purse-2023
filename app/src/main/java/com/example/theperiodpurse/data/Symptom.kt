package com.example.theperiodpurse.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.theperiodpurse.R

enum class Symptom(@StringRes val nameId: Int, @DrawableRes val resourceId: Int) {
    MOOD(R.string.mood, R.drawable.sentiment_neutral_black_24dp),
    EXERCISE(R.string.exercise, R.drawable.self_improvement_black_24dp),
    CRAMPS(R.string.cramps, R.drawable.sick_black_24dp),
    SLEEP(R.string.sleep, R.drawable.nightlight_black_24dp),
    FLOW(R.string.period_flow, R.drawable.opacity_black_24dp)
}
