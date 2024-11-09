package com.example.data.api

import com.example.data.dto.CompanyListDTO
import com.example.data.dto.CompanyDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyServiceApi {
    @GET("search/company")
    suspend fun searchCompany(
        @Query("query") keyword: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): CompanyListDTO

    @GET("companies/{company_id}")
    suspend fun getCompanyDetail(
        @Path("company_id") companyId: Int
    ): CompanyDetailDTO
}