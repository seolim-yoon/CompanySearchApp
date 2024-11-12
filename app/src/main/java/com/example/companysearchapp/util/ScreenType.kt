package com.example.companysearchapp.util

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Main: Route

    @Serializable
    data class Detail(
        val companyId: Int
    ): Route
}