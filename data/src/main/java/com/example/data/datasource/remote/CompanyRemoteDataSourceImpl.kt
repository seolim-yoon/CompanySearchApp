package com.example.data.datasource.remote

import com.example.data.api.CompanyServiceApi
import com.example.data.dto.CompanyDetailDTO
import com.example.data.dto.CompanyListDTO
import javax.inject.Inject

class CompanyRemoteDataSourceImpl @Inject constructor(
    private val companyServiceApi: CompanyServiceApi
) : CompanyRemoteDataSource {
    override suspend fun searchCompany(keyword: String, page: Int, pageSize: Int): CompanyListDTO =
        companyServiceApi.searchCompany(keyword = keyword, page = page, pageSize = pageSize)

    override suspend fun getCompanyDetail(companyId: Int): CompanyDetailDTO =
        companyServiceApi.getCompanyDetail(companyId = companyId)
}