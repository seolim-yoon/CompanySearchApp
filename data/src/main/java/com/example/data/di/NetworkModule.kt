package com.example.data.di

import com.example.data.api.CompanyServiceApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://openapi.wanted.jobs/v1/"
    private const val OPEN_API_CLIENT_ID = "ocir8SVWIv03EUm6vCC7u2Rm"
    private const val OPEN_API_CLIENT_SECRET = "dGaBNgY7M79X8jjKFq04XRnG4mr2y5pRRS1yDIM29NlOgiwL"
    private val contentType = "application/json".toMediaType()

    @Singleton
    @Provides
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("wanted-client-id", OPEN_API_CLIENT_SECRET)
                    .addHeader("wanted-client-secret", OPEN_API_CLIENT_ID)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        json: Json,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun providesCompanyServiceApi(retrofit: Retrofit): CompanyServiceApi =
        retrofit.create(CompanyServiceApi::class.java)
}