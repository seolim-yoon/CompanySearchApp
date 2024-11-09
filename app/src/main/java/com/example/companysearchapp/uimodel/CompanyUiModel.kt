package com.example.companysearchapp.uimodel

data class CompanyUiModelList(
    val companyList: List<CompanyUiModel>,
    val next: String
)
data class CompanyUiModel(
    val id: Int,
    val logoUrl: ImageUrl,
    val title: String,
    val description: String,
    val companyImageUrl: List<ImageUrl>,
    val url: String
) {
    data class ImageUrl(
        val id: Int = 0,
        val isTitle: Boolean = false,
        val origin: String,
        val thumbnail: String
    )
}
