package com.tpp.theperiodpurse.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = Purple500, primaryVariant = Purple700, secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ThePeriodPurseTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = HeaderColor1
    )
    systemUiController.setNavigationBarColor(
        color = BottomBarColor1
    )

    MaterialTheme(
        colors = colors, typography = Typography, shapes = Shapes, content = content
    )
}