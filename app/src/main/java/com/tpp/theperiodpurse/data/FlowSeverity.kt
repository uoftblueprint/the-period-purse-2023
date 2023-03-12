package com.tpp.theperiodpurse.data

enum class FlowSeverity(val displayName: String) {
    Light("Light"),
    Medium("Medium"),
    Heavy("Heavy"),
    Spotting("Spotting"),
    None("None");

    companion object {
        fun getSeverityByDisplayName(displayName: String): FlowSeverity? {
            return FlowSeverity.values().find{ it.displayName == displayName }
        }
    }
}