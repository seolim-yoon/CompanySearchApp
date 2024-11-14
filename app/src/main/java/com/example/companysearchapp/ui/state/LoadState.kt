package com.example.companysearchapp.ui.state

sealed interface LoadState {
    data object Loading : LoadState
    data object Success : LoadState
    data class Error(val error: Throwable) : LoadState
}
