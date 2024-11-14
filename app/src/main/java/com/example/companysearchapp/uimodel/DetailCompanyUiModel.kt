package com.example.companysearchapp.uimodel


data class DetailCompanyUiModel(
    val id: Int = 0,
    val logoUrl: ImageUiModel = ImageUiModel(),
    val title: String = "",
    val description: String = "",
    val companyImageUrl: List<ImageUiModel> = listOf(),
    val link: String = "",
    val url: String = ""
)