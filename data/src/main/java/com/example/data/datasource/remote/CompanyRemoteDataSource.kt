package com.example.data.datasource.remote

import com.example.data.dto.CompanyDetailDTO
import com.example.data.dto.CompanyListDTO

interface CompanyRemoteDataSource {
    suspend fun searchCompany(keyword: String, offset: Int, limit: Int): CompanyListDTO
    suspend fun getCompanyDetail(companyId: Int): CompanyDetailDTO
}