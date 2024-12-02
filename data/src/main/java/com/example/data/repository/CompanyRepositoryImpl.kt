package com.example.data.repository

import com.example.data.api.CompanyServiceApi
import com.example.data.mapper.CompanyEntityMapper
import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyListEntity
import com.example.domain.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyServiceApi: CompanyServiceApi,
    private val companyEntityMapper: CompanyEntityMapper
) : CompanyRepository {
    override suspend fun searchCompany(
        keyword: String,
        offset: Int,
        limit: Int
    ): CompanyListEntity =
        withContext(Dispatchers.IO) {
            companyEntityMapper.mapToCompanyListEntity(
                companyServiceApi.searchCompany(
                    keyword = keyword,
                    offset = offset,
                    limit = limit
                )
            )
        }


    override suspend fun getCompanyDetail(companyId: Int): CompanyDetailEntity =
        withContext(Dispatchers.IO) {
            companyEntityMapper.mapToCompanyDetailEntity(
                companyServiceApi.getCompanyDetail(
                    companyId = companyId
                )
            )
        }

}