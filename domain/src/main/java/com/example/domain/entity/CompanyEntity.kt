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
    val company: CompanyEntity = CompanyEntity()
)

data class CompanyEntity(
    val id: Int = 0,
    val name: String = "",
    val url: String = "",
    val description: String = "",
    // company
    val logoImg: ImageEntity = ImageEntity(),
    val titleImg: ImageEntity = ImageEntity(),
    // detail
    val companyConfirm: Boolean = false,
    val images: List<ImageEntity> = listOf(),
    val link: String = "",
    val logoUrl: ImageEntity = ImageEntity(),
    val registrationNumber: String = "",
) {
    data class ImageEntity(
        val id: Int = 0,
        val isTitle: Boolean = false,
        val origin: String = "",
        val thumb: String = ""
    )
}
