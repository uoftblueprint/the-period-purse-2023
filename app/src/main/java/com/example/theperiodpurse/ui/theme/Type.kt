package com.example.theperiodpurse.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R

val Avenir = FontFamily(
    Font(R.font.avenir_book)
)

val Avenir_Black = FontFamily(
    Font(R.font.avenir_black)
)


// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    body2 = TextStyle(
            fontFamily = Avenir_Black,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
    )

)