package com.example.domain.repository

import com.example.domain.entity.CompanyDetailEntity
import com.example.domain.entity.CompanyListEntity
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    suspend fun searchCompany(keyword: String, offset: Int, limit: Int): Flow<CompanyListEntity>
    suspend fun getCompanyDetail(companyId: Int): CompanyDetailEntity
}