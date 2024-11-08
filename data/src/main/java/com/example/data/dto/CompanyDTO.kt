package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDTO(
    val id: Int = 0,
    val name: String = "",
    val url: String = "",
    val description: String = "",
    // company
    @SerialName("logo_img")
    val logoImg: Image = Image(),
    @SerialName("title_img")
    val titleImg: Image = Image(),
    // detail
    @SerialName("company_confirm")
    val companyConfirm: Boolean = false,
    val images: List<Image> = listOf(),
    val link: String = "",
    @SerialName("logo_url")
    val logoUrl: Image = Image(),
    @SerialName("registration_number")
    val registrationNumber: String = "",
) {
    @Serializable
    data class Image(
        val id: Int = 0,
        @SerialName("is_title")
        val isTitle: Boolean = false,
        val origin: String = "",
        val thumb: String = ""
    )
}