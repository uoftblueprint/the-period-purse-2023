package com.example.theperiodpurse.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date")
data class Date (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
//    val date: Date,
    val flow: FlowSeverity,
    val mood: Mood,
//    val exerciseLength: Date,
    val exerciseType: Exercise,
    val crampSeverity: CrampSeverity,
//    val sleep: Date
    )