package com.tpp.theperiodpurse.ui.theme

import androidx.compose.ui.graphics.Color


interface ColorPalette {
    val primary: Color
    val background: Color
    // Add other color properties as needed for your app's design
}

class DarkColorPaletteImpl : ColorPalette {
    override val primary = Color(0xFFBB86FC)
    override val background = Color(0xFF121212)
    // Implement other color properties for dark mode
}

class LightColorPaletteImpl : ColorPalette {
    override val primary = Color(0xFF6200EE)
    override val background = Color.White
    // Implement other color properties for light mode
}

