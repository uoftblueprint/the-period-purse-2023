package com.tpp.theperiodpurse.ui.symptomlog

data class LogUiState (
    var selectSquares: LinkedHashMap<Int, Any>,
    var promptToText: LinkedHashMap<Int, String>
)