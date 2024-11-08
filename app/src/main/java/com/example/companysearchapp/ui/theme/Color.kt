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

class CompanyColorScheme(
    gray: Color = Gray,
    lightGray: Color = LightGray,
    white: Color = White
) {
    var gray by mutableStateOf(gray)
        private set

    var lightGray by mutableStateOf(lightGray)
        private set

    var white by mutableStateOf(white)
        private set

    fun copy(
        gray: Color = this.gray,
        lightGray: Color = this.lightGray,
        white: Color = this.white
    ) = CompanyColorScheme(
        gray = gray,
        lightGray = lightGray,
        white = white
    )

    fun updateColorsFrom(other: CompanyColorScheme) {
        gray = other.gray
        lightGray = other.lightGray
        white = other.white
    }
}

val LocalColorScheme = staticCompositionLocalOf {
    CompanyColorScheme()
}