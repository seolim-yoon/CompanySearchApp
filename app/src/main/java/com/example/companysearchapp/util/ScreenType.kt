package com.example.companysearchapp.util

import kotlinx.serialization.Serializable

sealed interface ScreenType {
    @Serializable
    data object MainScreen: ScreenType

    @Serializable
    data class DetailScreen(
        val companyId: Int
    ): ScreenType
}