package com.example.theperiodpurse.data

enum class Exercise {
    CARDIO,
    YOGA,
    STRENGTH,
    BALL_SPORTS,
    MARTIAL_ARTS,
    WATER_SPORTS,
    CYCLE_SPORTS,
    RACKET_SPORTS,
    WINTER_SPORTS;

    companion object {
        private val map = Exercise.values().associateBy { it.name }
        infix fun from(name: String?) = map[name]
    }
}