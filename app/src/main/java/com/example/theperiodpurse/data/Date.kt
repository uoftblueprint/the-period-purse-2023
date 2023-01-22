package com.example.theperiodpurse.data
import java.util.Date
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "dates")
data class Date (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val dateID: UUID,

    @Column(name = "date", nullable = false)
    val date: Date,

    @Column(name = "flow", nullable = false)
    val flow: FlowSeverity,

    @Column(name = "mood", nullable = false)
    val mood: Mood,

    @Column(name = "exercise_length", nullable = false)
    val exerciseLength: Date,

    @Column(name = "exercise_type", nullable = false)
    val exerciseType: Exercise,

    @Column(name = "cramp_severity", nullable = false)
    val crampSeverity: CrampSeverity,

    @Column(name = "sleep", nullable = false)
    val sleep: Date
    )