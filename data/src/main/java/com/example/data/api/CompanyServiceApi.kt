package com.example.data.api

import com.example.data.dto.CompanyListDTO
import com.example.data.dto.CompanyDetailDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyServiceApi {
    @GET("search/company")
    suspend fun searchCompany(
        @Query("query") keyword: String,
        @Query("offset") page: Int,
        @Query("limit") pageSize: Int
    ): CompanyListDTO

    @GET("search/company")
    suspend fun getCompanyDetail(
        @Query("company_id") companyId: Int
    ): CompanyDetailDTO
}