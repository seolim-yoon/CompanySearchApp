package com.example.data.mapper

import com.example.data.dto.CompanyDTO
import com.example.data.dto.CompanyDetailDTO
import com.example.data.dto.CompanyListDTO
import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyEntity
import com.example.domain.entity.CompanyListEntity
import javax.inject.Inject

class CompanyEntityMapper @Inject constructor() {
    fun mapToCompanyListEntity(companyListDTO: CompanyListDTO): CompanyListEntity =
        CompanyListEntity(
            companies = mapToCompanyEntityList(companyListDTO.companies),
            links = mapToLinksEntity(companyListDTO.links)
        )

    fun mapToCompanyDetailEntity(companyDetailDTO: CompanyDetailDTO): CompanyDetailEntity =
        CompanyDetailEntity(
            company = mapToCompanyEntity(companyDetailDTO.company)
        )

    private fun mapToCompanyEntityList(
        companyList: List<CompanyDTO>,
    ): List<CompanyEntity> =
        companyList.map { company ->
            mapToCompanyEntity(company)
        }

    private fun mapToCompanyEntity(company: CompanyDTO): CompanyEntity =
        CompanyEntity(
            id = company.id,
            name = company.name,
            url = company.url,
            description = company.description,
            logoImg = mapToImageEntity(company.logoImg),
            titleImg = mapToImageEntity(company.titleImg),
            companyConfirm = company.companyConfirm,
            images = company.images.map { img ->
                mapToImageEntity(img)
            },
            link = company.link,
            logoUrl = mapToImageEntity(company.logoUrl),
            registrationNumber = company.registrationNumber
        )

    private fun mapToImageEntity(imageUrl: CompanyDTO.Image): CompanyEntity.ImageEntity =
        imageUrl.run {
            CompanyEntity.ImageEntity(
                id = id,
                isTitle = isTitle,
                origin = origin,
                thumb = thumb
            )
        }

    private fun mapToLinksEntity(links: CompanyListDTO.Links): CompanyListEntity.LinksEntity =
        links.run {
            CompanyListEntity.LinksEntity(
                next = next
            )
        }
}