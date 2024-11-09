package com.example.domain.repository

import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyListEntity

interface CompanyRepository {
    suspend fun searchCompany(keyword: String, offset: Int, limit: Int): CompanyListEntity
    suspend fun getCompanyDetail(companyId: Int): CompanyDetailEntity
}