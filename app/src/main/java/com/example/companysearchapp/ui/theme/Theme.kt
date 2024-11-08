package com.example.companysearchapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun CompanySearchAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: CompanyColorScheme = CompanySearchAppTheme.colors,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val currentColor = remember { colors }
    val rememberedColors = remember { currentColor.copy() }.apply { updateColorsFrom(currentColor) }

    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalColorScheme provides rememberedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}


object CompanySearchAppTheme {
    val typography: CompanyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colors: CompanyColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current
}