package com.example.domain.entity

data class CompanyListEntity(
    val companies: List<CompanyEntity>,
    val links: LinksEntity
) {
    data class LinksEntity(
        val next: String
    )
}

data class CompanyDetailEntity(
    val company: CompanyEntity
)

data class CompanyEntity(
    val id: Int,
    val name: String,
    val url: String,
    val description: String,
    // company
    val logoImg: ImageEntity,
    val titleImg: ImageEntity,
    // detail
    val companyConfirm: Boolean,
    val images: List<ImageEntity>,
    val link: String,
    val logoUrl: ImageEntity,
    val registrationNumber: String,
) {
    data class ImageEntity(
        val id: Int,
        val isTitle: Boolean,
        val origin: String,
        val thumb: String
    )
}
