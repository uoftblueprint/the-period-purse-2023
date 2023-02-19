package com.example.theperiodpurse.data

enum class Mood {
    HAPPY,
    NEUTRAL,
    SAD,
    LOL,
    IDK,
    GREAT,
    SICK,
    ANGRY,
    LOVED;

    companion object {
        private val map = Mood.values().associateBy { it.name }
        infix fun from(name: String?) = map[name]
    }
}