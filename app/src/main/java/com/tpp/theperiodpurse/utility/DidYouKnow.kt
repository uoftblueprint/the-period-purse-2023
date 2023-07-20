package com.tpp.theperiodpurse.utility

import android.content.Context
import android.content.SharedPreferences
import com.tpp.theperiodpurse.R
import java.io.InputStream
import java.time.LocalDate

private const val SHARED_PREFS_NAME = "SharedPreferences"

fun readRandomLine(context: Context): String {
    val inputStream: InputStream = context.resources.openRawResource(R.raw.dykfacts)
    return inputStream.bufferedReader().useLines {
        it.elementAtOrNull((0..28).random()) ?: ""
    }
}

fun getSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

fun setFact(context: Context) {
    val sharedPreferences = getSharedPreferences(context)

    val c = LocalDate.now().toString()
    if (c != getLastDate(context)) {
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("FACT_KEY", readRandomLine(context))
            putString("DATE_KEY", c)
        }.apply()
    }
}

fun getLastDate(context: Context): String? {
    val sharedPreferences = getSharedPreferences(context)
    return sharedPreferences.getString("DATE_KEY", null)
}

fun getFact(context: Context): String? {
    val sharedPreferences = getSharedPreferences(context)
    return sharedPreferences.getString("FACT_KEY", null)
}

/**
 * Given a string and max length, returns a new string up to the new length
 * followed by an ellipses.
 */
fun getSubString(s: String, maxLength: Int): String {
    return if (s.length >= maxLength) {
        s.substring(0, maxLength) + "..."
    }
    else {
        s
    }
}
