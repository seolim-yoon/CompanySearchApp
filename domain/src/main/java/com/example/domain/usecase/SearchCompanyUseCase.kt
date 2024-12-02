package com.example.domain.usecase

import com.example.domain.entity.CompanyListEntity
import com.example.domain.repository.CompanyRepository
import javax.inject.Inject

class SearchCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(
        keyword: String,
        offset: Int,
        limit: Int
    ): CompanyListEntity =
        companyRepository.searchCompany(
            keyword = keyword,
            offset = offset,
            limit = limit
        )
}