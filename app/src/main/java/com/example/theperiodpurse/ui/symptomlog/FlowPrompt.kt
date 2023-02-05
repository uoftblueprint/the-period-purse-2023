package com.example.theperiodpurse.ui.symptomlog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.theperiodpurse.data.LogPrompt
import com.example.theperiodpurse.data.LogSquare
import com.example.theperiodpurse.ui.calendar.LogSelectableSquare

@Composable
fun FlowPrompt (logViewModel: LogViewModel) {
    var selected by remember {
        mutableStateOf(logViewModel.getSquareSelected(logPrompt = LogPrompt.Flow))
    }
    Column() {
        val flowSquares = listOf(
            LogSquare.FlowLight,
            LogSquare.FlowMedium,
            LogSquare.FlowHeavy,
            LogSquare.FlowSpotting,
            LogSquare.FlowNone
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = 0.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 16.dp),
            modifier = Modifier
                .height(270.dp),
            content = {
                items(flowSquares) { flowSquare ->
                    LogSelectableSquare(
                        logSquare = flowSquare,
                        selected = selected
                    ) { logSquare ->
                        if (selected == logSquare.description) {
                            selected = null
                            logViewModel.resetSquareSelected(logSquare)
                        } else {
                            selected = logSquare.description
                            logViewModel.setSquareSelected(logSquare)
                        }
                    }
                }
        })

    }
}


@Preview
@Composable
fun FlowPromptPreview() {
    FlowPrompt(
        logViewModel = LogViewModel(
            logPrompts = listOf(LogPrompt.Flow),
            viewModel()
        )
    )
}