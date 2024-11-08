package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompanyDetailDTO(
    val company: CompanyDTO = CompanyDTO()
)
