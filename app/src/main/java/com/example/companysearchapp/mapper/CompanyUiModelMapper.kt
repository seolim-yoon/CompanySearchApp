package com.example.companysearchapp.mapper

import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.uimodel.CompanyUiModelList
import com.example.domain.entity.CompanyEntity
import com.example.domain.entity.CompanyListEntity
import javax.inject.Inject

class CompanyUiModelMapper @Inject constructor() {
    fun mapToCompanyListUiModel(companyList: CompanyListEntity): CompanyUiModelList =
        CompanyUiModelList(
            companyList = companyList.companies.map { company ->
                mapToCompanyUiModel(company)
            },
            next = companyList.links.next
        )

    fun mapToCompanyUiModel(company: CompanyEntity): CompanyUiModel =
        company.run {
            CompanyUiModel(
                id = id,
                logoUrl = mapToLogoImages(logoUrl),
                title = name,
                description = description,
                companyImageUrl = mapToCompanyImages(images),
                url = url

            )
        }

    private fun mapToLogoImages(image: CompanyEntity.ImageEntity): CompanyUiModel.ImageUrl =
        image.run {
            CompanyUiModel.ImageUrl(
                id = id,
                isTitle = isTitle,
                origin = origin,
                thumbnail = thumb
            )
        }

    private fun mapToCompanyImages(images: List<CompanyEntity.ImageEntity>): List<CompanyUiModel.ImageUrl> =
        images.map { image ->
            CompanyUiModel.ImageUrl(
                id = image.id,
                isTitle = image.isTitle,
                origin = image.origin,
                thumbnail = image.thumb
            )
        }

}