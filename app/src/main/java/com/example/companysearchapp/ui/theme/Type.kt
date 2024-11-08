package com.example.companysearchapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CompanyTypography(
    val heading20: TextStyle = TextStyle.Default,
    val heading18: TextStyle = TextStyle.Default,
    val body16: TextStyle = TextStyle.Default,
    val body14: TextStyle = TextStyle.Default
)

val Typography = CompanyTypography(
    heading20 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    heading18 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp
    ),
    body16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp
    ),
    body14 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    )
)

val LocalTypography = staticCompositionLocalOf {
    CompanyTypography()
}