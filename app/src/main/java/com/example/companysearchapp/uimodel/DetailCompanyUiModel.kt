package com.example.companysearchapp.uimodel


data class DetailCompanyUiModel(
    val id: Int,
    val logoUrl: ImageUiModel,
    val title: String,
    val description: String,
    val companyImageUrl: List<ImageUiModel>,
    val url: String
)