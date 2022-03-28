package com.codelab.theming.ui.start.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Red200 = Color(0xfff297a2)
val Red300 = Color(0xffea6d7e)
val Red700 = Color(0xffdd0d3c)
val Red800 = Color(0xffd00036)

val bluegrey = Color(0xfffafafa)
val GreenSoft = Color(0xffA5D6A7)
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val lightGreen200 = Color(0x9932CD32)

val Colors.lightGreen: Color
    @Composable
    get() = lightGreen200
val Color.lightGrey: Color
    @Composable
    get() = bluegrey