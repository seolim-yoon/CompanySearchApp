package com.example.data.repository

import com.example.data.datasource.remote.CompanyRemoteDataSource
import com.example.data.mapper.CompanyEntityMapper
import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyListEntity
import com.example.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyRemoteDataSource: CompanyRemoteDataSource,
    private val companyEntityMapper: CompanyEntityMapper
) : CompanyRepository {
    override suspend fun searchCompany(
        keyword: String,
        offset: Int,
        limit: Int
    ): Flow<CompanyListEntity> =
        flow {
            companyRemoteDataSource.searchCompany(
                keyword = keyword,
                offset = offset,
                limit = limit
            ).collect {
                emit(companyEntityMapper.mapToCompanyListEntity(it))
            }
        }

    override suspend fun getCompanyDetail(companyId: Int): CompanyDetailEntity =
        companyEntityMapper.mapToCompanyDetailEntity(
            companyRemoteDataSource.getCompanyDetail(
                companyId = companyId
            )
        )
}