package com.example.theperiodpurse.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SymptomConverter {
    @TypeConverter
    fun fromSymptomList(symptomList: ArrayList<Symptom>): String {
        val gson = Gson()
        return gson.toJson(symptomList)
    }

    @TypeConverter
    fun toSymptomList(symptomList: String): ArrayList<Symptom> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Symptom>>() {}.type
        return gson.fromJson(symptomList, type)
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

class DaysConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): java.util.Date? {
        return if (value == null) null else java.util.Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long? {
        return date?.time
    }
}