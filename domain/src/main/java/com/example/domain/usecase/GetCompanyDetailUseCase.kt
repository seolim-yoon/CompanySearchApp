package com.example.domain.usecase

import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.repository.CompanyRepository
import javax.inject.Inject

class GetCompanyDetailUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(
        companyId: Int
    ): CompanyDetailEntity =
        companyRepository.getCompanyDetail(
            companyId = companyId
        )
}