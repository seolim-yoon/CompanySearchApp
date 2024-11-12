package com.example.companysearchapp

sealed interface UiEvent {
    data class InputKeyword(val keyword: String) : UiEvent
    data object Refresh : UiEvent
    data object LoadMore : UiEvent
}
