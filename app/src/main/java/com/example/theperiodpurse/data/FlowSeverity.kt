package com.example.theperiodpurse.data

enum class FlowSeverity {
    LIGHT,
    MEDIUM,
    HEAVY,
    SPOTTING,
    NONE;

    companion object {
        private val map = values().associateBy { it.name }
        infix fun from(name: String?) = map[name]
    }
}