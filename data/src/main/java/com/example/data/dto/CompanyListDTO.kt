package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyListDTO(
    val companies: List<CompanyDTO> = listOf(),
    val links: Links = Links()
) {
    @Serializable
    data class Links(
        val next: String = ""
    )
}