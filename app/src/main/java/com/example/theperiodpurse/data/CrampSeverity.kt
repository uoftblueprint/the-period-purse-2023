package com.example.theperiodpurse.data

enum class CrampSeverity {
    Neutral,
    Bad,
    Terrible,
    Good,
    None;

    companion object {
        private val map = CrampSeverity.values().associateBy { it.name }
        infix fun from(name: String?) = map[name]
    }
}