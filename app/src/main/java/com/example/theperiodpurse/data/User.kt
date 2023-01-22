package com.example.theperiodpurse.data

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID,

    @OneToMany
    @Column(name = "symptoms_to_track")
    val symptomsToTrack: ArrayList<Symptom> = ArrayList(),

    @OneToMany
    @Column(name = "period_history")
    val periodHistory: ArrayList<Date> = ArrayList(),

    @Column(name = "average_period_length")
    var averagePeriodLength: Int = 0,

    @Column(name = "average_cycle_length")
    var averageCycleLength: Int = 0,

    @Column(name = "days_since_last_period")
    var daysSinceLastPeriod: Int = 0
    )