package com.example.companysearchapp

import com.example.companysearchapp.base.BaseViewModel
import com.example.companysearchapp.base.UiState
import com.example.companysearchapp.mapper.CompanyUiModelMapper
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.util.PAGE_SIZE
import com.example.companysearchapp.util.SEARCH_TIME_DELAY
import com.example.domain.usecase.SearchCompanyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MainUiState(
    val isEnd: Boolean = false,
    val mainLoadState: MainLoadState = LoadState.Success(listOf())
) : UiState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchCompanyUseCase: SearchCompanyUseCase,
    private val companyUiModelMapper: CompanyUiModelMapper
) : BaseViewModel<MainUiState>() {

    private var currentPage = 0

    private val _currentKeyword: MutableStateFlow<String> = MutableStateFlow("")
    val currentKeyword = _currentKeyword.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val searchResult = _currentKeyword
        .debounce(SEARCH_TIME_DELAY)
        .distinctUntilChanged()
        .filter { it.isNotEmpty() }
        .flatMapLatest { keyword ->
            setState {
                copy(mainLoadState = LoadState.Loading)
            }
            searchCompanyUseCase(
                keyword = keyword,
                offset = 0,
                limit = PAGE_SIZE
            )
        }

    init {
        searchCompanyByKeyword()
    }

    override fun createInitialState(): MainUiState = MainUiState()

    private fun searchCompanyByKeyword() {
        viewModelLaunch(onSuccess = {
            searchResult
                .collect { result ->
                    val searchResult = companyUiModelMapper.mapToCompanyListUiModel(result)
                    setState {
                        copy(
                            isEnd = searchResult.next.isEmpty(),
                            mainLoadState = LoadState.Success(
                                mutableListOf<CompanyUiModel>().apply {
                                    addAll(searchResult.companyList)
                                }
                            )
                        )
                    }
                }
        })
    }

    private fun inputSearchKeyword(keyword: String) {
        currentPage = 0
        _currentKeyword.update { keyword }

        if (keyword.isEmpty()) {
            setState {
                copy(
                    mainLoadState = LoadState.Success(emptyList())
                )
            }
        }
    }

    private fun loadMoreCompanyList(page: Int) {
        viewModelLaunch(onSuccess = {
            searchCompanyUseCase(
                keyword = _currentKeyword.value,
                offset = page * PAGE_SIZE,
                limit = PAGE_SIZE
            ).collect { result ->
                val loadMoreResult = companyUiModelMapper.mapToCompanyListUiModel(result)

                setState {
                    (currentState.mainLoadState as? LoadState.Success)?.let { successState ->
                        copy(
                            isEnd = loadMoreResult.next.isEmpty(),
                            mainLoadState = LoadState.Success(
                                successState.data.toMutableList().apply {
                                    addAll(loadMoreResult.companyList)
                                }
                            )
                        )
                    } ?: currentState
                }
            }
        })
    }

    override fun handleException(throwable: Throwable) {
        setState {
            copy(
                mainLoadState = LoadState.Error(throwable)
            )
        }
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.InputKeyword -> inputSearchKeyword(event.keyword)

            is UiEvent.Refresh -> searchCompanyByKeyword()

            is UiEvent.LoadMore -> loadMoreCompanyList(++currentPage)
        }
    }
}