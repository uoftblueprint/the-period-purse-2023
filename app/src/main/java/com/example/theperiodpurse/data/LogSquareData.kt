package com.example.theperiodpurse.data

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.theperiodpurse.R

typealias ComposableIconFun = @Composable () -> Unit

open class LogSquare (var description: String, var icon: ComposableIconFun) {
    object FlowLight: LogSquare(
        description = "Light",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.water_drop_black_24dp),
                contentDescription = "FlowLight"
            )
        }
    )
    object FlowMedium: LogSquare(
        description = "Medium",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.opacity_black_24dp),
                contentDescription = "FlowMedium"
            )
        }
    )
    object FlowHeavy: LogSquare(
        description = "Heavy",
        icon = {
            Icon(
                // Missing asset
                painter = painterResource(id = R.drawable.menu_black_24dp),
                contentDescription = "FlowHeavy"
            )
        }
    )
    object FlowSpotting: LogSquare(
        description = "Spotting",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.spotting),
                contentDescription = "FlowSpotting"
            )
        }
    )
    object FlowNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.not_interested_black_24dp),
                contentDescription = "FlowNone"
            )
        }
    )
}