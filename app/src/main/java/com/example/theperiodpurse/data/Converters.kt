package com.example.theperiodpurse.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SymptomConverter {
    @TypeConverter
    fun fromSymptomList(symptoms: List<Symptom>): String {
        return symptoms.joinToString(",")
    }

    @TypeConverter
    fun toSymptomList(symptoms: String): List<Symptom> {
        return symptoms.split(",").map { Symptom.valueOf(it) }
    }
}

class DateConverter {
    @TypeConverter
    fun fromDateList(dateList: ArrayList<Date>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Date>>() {}.type
        return gson.toJson(dateList, type)
    }

    @TypeConverter
    fun toDateList(dateList: String): ArrayList<Date> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Date>>() {}.type
        return gson.fromJson(dateList, type)
    }
}