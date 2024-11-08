package com.example.domain.repository

import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyListEntity

interface CompanyRepository {
    suspend fun searchCompany(keyword: String, page: Int, pageSize: Int): CompanyListEntity
    suspend fun getCompanyDetail(companyId: Int): CompanyDetailEntity
}