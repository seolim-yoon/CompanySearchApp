package com.example.companysearchapp.uimodel

data class CompanyUiModelList(
    val companyList: List<CompanyUiModel>,
    val next: String
)
data class CompanyUiModel(
    val id: Int,
    val logoImg: ImageUiModel,
    val title: String,
    val description: String
)