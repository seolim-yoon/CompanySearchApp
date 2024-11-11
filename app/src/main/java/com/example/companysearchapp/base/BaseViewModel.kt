package com.example.companysearchapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companysearchapp.UiEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState

abstract class BaseViewModel<State: UiState> : ViewModel() {
    private val initialState : State by lazy { createInitialState() }
    abstract fun createInitialState() : State

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected fun setState(reduce: State.() -> State) {
        _uiState.update {
            currentState.reduce()
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    abstract fun handleException(throwable: Throwable)

    open fun viewModelLaunch(
        onSuccess: suspend () -> Unit
    ) {
        viewModelScope.launch(
            context = exceptionHandler
        ) {
            onSuccess.invoke()
        }
    }

    abstract fun onEvent(event: UiEvent)
}