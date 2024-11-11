package com.example.companysearchapp.mapper

import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.uimodel.CompanyUiModelList
import com.example.companysearchapp.uimodel.DetailCompanyUiModel
import com.example.companysearchapp.uimodel.ImageUiModel
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

    private fun mapToCompanyUiModel(company: CompanyEntity): CompanyUiModel =
        company.run {
            CompanyUiModel(
                id = id,
                logoImg = mapToLogoImages(logoImg),
                title = name,
                description = description
            )
        }

    private fun mapToLogoImages(image: CompanyEntity.ImageEntity): ImageUiModel =
        image.run {
            ImageUiModel(
                id = id,
                isTitle = isTitle,
                origin = origin,
                thumbnail = thumb
            )
        }

    private fun mapToCompanyImages(images: List<CompanyEntity.ImageEntity>): List<ImageUiModel> =
        images.map { image ->
            ImageUiModel(
                id = image.id,
                isTitle = image.isTitle,
                origin = image.origin,
                thumbnail = image.thumb
            )
        }

    fun mapToDetailCompanyUiModel(company: CompanyEntity): DetailCompanyUiModel =
        company.run {
            DetailCompanyUiModel(
                id = id,
                logoUrl = mapToLogoImages(logoUrl),
                title = name,
                description = description,
                companyImageUrl = mapToCompanyImages(images),
                link = link,
                url = url
            )
        }
}