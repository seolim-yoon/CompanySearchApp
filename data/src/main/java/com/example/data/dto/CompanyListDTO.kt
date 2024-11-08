package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompanyListDTO(
    val companies: List<CompanyDTO> = listOf(),
    val links: Links = Links()
) {
    @Serializable
    data class Links(
        val prev: String = "",
        val next: String = ""
    )
}