package com.example.domain.usecase

import com.example.domain.entity.CompanyListEntity
import com.example.domain.repository.CompanyRepository
import javax.inject.Inject

class SearchCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(
        keyword: String,
        page: Int,
        pageSize: Int
    ): CompanyListEntity =
        companyRepository.searchCompany(
            keyword = keyword,
            page = page,
            pageSize = pageSize
        )
}