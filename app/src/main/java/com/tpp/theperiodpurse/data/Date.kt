package com.tpp.theperiodpurse.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.Duration
import java.util.Date

@Entity(tableName = "dates")
data class Date (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(DaysConverter::class)
    val date: Date?,
    val flow: FlowSeverity?,
    val mood: Mood?,
    @TypeConverters(DurationConverter::class)
    val exerciseLength: Duration?,
    val exerciseType: Exercise?,
    val crampSeverity: CrampSeverity?,
    @TypeConverters(DurationConverter::class)
    val sleep: Duration?,
    val notes: String
    )