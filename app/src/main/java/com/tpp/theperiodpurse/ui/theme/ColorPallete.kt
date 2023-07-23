package com.tpp.theperiodpurse.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.tpp.theperiodpurse.R


interface ColorPalette {
    val primary1: Color
    val primary2: Color
    val primary3: Color
    val secondary1: Color
    val secondary2: Color
    val secondary3: Color
    val secondary4: Color
    val text1: Color
    val text2: Color

    val background: Int
    // Add other color properties as needed for your app's design
}

class DarkColorPaletteImpl : ColorPalette {
    override val primary1 = Color(114, 198, 176)
    override val primary2 = Color(	240, 46, 48)
    override val primary3 = Color(125, 161, 152)
    override val secondary1 = Color(114, 198, 176)
    override val secondary2 = Color(115, 199, 183)
    override val secondary3 = Color(250, 103, 103)
    override val secondary4 = Color(161, 162, 166)
    override val text1 = Color(	217, 218, 217)
    override val text2 = Color(161, 162, 166)


    override val background: Int= R.drawable.darkwatercolour

    // Implement other color properties for dark mode
}

class LightColorPaletteImpl : ColorPalette {
    override val primary1 = Color(114, 198, 176)
    override val primary2 = Color(	211, 39, 41)
    override val primary3 = Color(125, 161, 152)
    override val secondary1 = Color(90, 159, 147)
    override val secondary2 = Color(115, 199, 183)
    override val secondary3 = Color(179, 31, 32)
    override val secondary4 = Color(109, 110, 113)
    override val text1 = Color.DarkGray
    override val text2 = Color.Gray

    override val background: Int = R.drawable.background
    // Implement other color properties for light mode
}

