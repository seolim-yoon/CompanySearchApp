package com.example.data.repository

import com.example.data.api.CompanyServiceApi
import com.example.data.mapper.CompanyEntityMapper
import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyListEntity
import com.example.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyServiceApi: CompanyServiceApi,
    private val companyEntityMapper: CompanyEntityMapper
) : CompanyRepository {
    override suspend fun searchCompany(
        keyword: String,
        offset: Int,
        limit: Int
    ): Flow<CompanyListEntity> =
        flow {
            emit(
                companyEntityMapper.mapToCompanyListEntity(
                    companyServiceApi.searchCompany(
                        keyword = keyword,
                        offset = offset,
                        limit = limit
                    )
                )
            )
        }

    override suspend fun getCompanyDetail(companyId: Int): CompanyDetailEntity =
        companyEntityMapper.mapToCompanyDetailEntity(
            companyServiceApi.getCompanyDetail(
                companyId = companyId
            )
        )
}