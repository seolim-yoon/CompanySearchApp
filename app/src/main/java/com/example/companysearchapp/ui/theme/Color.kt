package com.example.companysearchapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Gray = Color(0xFF121212)
val LightGray = Color(0xFF6B6B72)
val White = Color(0xFFFFFFFF)
val Blue = Color(0xFF0E46B3)

class CompanyColorScheme(
    gray: Color = Gray,
    lightGray: Color = LightGray,
    white: Color = White,
    blue: Color = Blue
) {
    var gray by mutableStateOf(gray)
        private set

    var lightGray by mutableStateOf(lightGray)
        private set

    var white by mutableStateOf(white)
        private set

    var blue by mutableStateOf(blue)
        private set

    fun copy(
        gray: Color = this.gray,
        lightGray: Color = this.lightGray,
        white: Color = this.white,
        blue: Color = this.blue
    ) = CompanyColorScheme(
        gray = gray,
        lightGray = lightGray,
        white = white,
        blue = blue
    )

    fun updateColorsFrom(other: CompanyColorScheme) {
        gray = other.gray
        lightGray = other.lightGray
        white = other.white
        blue = other.blue
    }
}

val LocalColorScheme = staticCompositionLocalOf {
    CompanyColorScheme()
}