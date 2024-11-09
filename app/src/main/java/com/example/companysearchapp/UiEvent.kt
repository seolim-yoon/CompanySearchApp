package com.example.companysearchapp

sealed interface UiEvent {
    data class SearchCompany(val keyword: String) : UiEvent
    data object Refresh : UiEvent
    data object LoadMore : UiEvent
    data class RequestDetailInfo(val companyId: Int) : UiEvent
}
