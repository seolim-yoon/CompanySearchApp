package com.example.companysearchapp.ui.state

import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.uimodel.DetailCompanyUiModel

sealed interface LoadState<out T> {
    data object Loading : LoadState<Nothing>
    data class Success<T>(val data: T) : LoadState<T>
    data class Error(val error: Throwable) : LoadState<Nothing>
}

typealias MainLoadState = LoadState<List<CompanyUiModel>>
typealias DetailLoadState = LoadState<DetailCompanyUiModel>
