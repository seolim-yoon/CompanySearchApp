package com.example.data.repository

import com.example.data.datasource.remote.CompanyRemoteDataSource
import com.example.data.mapper.CompanyEntityMapper
import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyListEntity
import com.example.domain.repository.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyRemoteDataSource: CompanyRemoteDataSource,
    private val companyEntityMapper: CompanyEntityMapper
): CompanyRepository {
    override suspend fun searchCompany(keyword: String, page: Int, pageSize: Int): CompanyListEntity =
        companyEntityMapper.mapToCompanyListEntity(companyRemoteDataSource.searchCompany(keyword = keyword, page = page, pageSize = pageSize))

    override suspend fun getCompanyDetail(companyId: Int): CompanyDetailEntity =
        companyEntityMapper.mapToCompanyDetailEntity(companyRemoteDataSource.getCompanyDetail(companyId = companyId))
}