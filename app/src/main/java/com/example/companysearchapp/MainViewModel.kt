package com.example.companysearchapp

import com.example.companysearchapp.base.BaseViewModel
import com.example.companysearchapp.base.UiState
import com.example.companysearchapp.mapper.CompanyUiModelMapper
import com.example.companysearchapp.ui.event.UiEvent
import com.example.companysearchapp.ui.state.LoadState
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
    val companyList: List<CompanyUiModel> = listOf(),
    val mainLoadState: LoadState = LoadState.Success
) : UiState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchCompanyUseCase: SearchCompanyUseCase,
    private val companyUiModelMapper: CompanyUiModelMapper
) : BaseViewModel<MainUiState>() {

    private var currentPage = 0
    private var isLoadingPaging = false

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
        searchCompanyByKeyword(isLoadMore = false)
    }

    override fun createInitialState(): MainUiState = MainUiState()

    private fun searchCompanyByKeyword(isLoadMore: Boolean) {
        if (isLoadingPaging) return

        viewModelLaunch(onSuccess = {
            isLoadingPaging = true

            val entityResult = if (isLoadMore) searchCompanyUseCase(
                keyword = _currentKeyword.value,
                offset = (++currentPage) * PAGE_SIZE,
                limit = PAGE_SIZE
            ) else searchResult

            entityResult.collect { result ->
                val uiModelResult = companyUiModelMapper.mapToCompanyListUiModel(result)

                setState {
                    copy(
                        isEnd = uiModelResult.next.isEmpty(),
                        companyList = companyList.toMutableList().apply {
                            addAll(uiModelResult.companyList)
                        }.distinct(),
                        mainLoadState = LoadState.Success
                    )
                }

                isLoadingPaging = false
            }
        })
    }

    private fun inputSearchKeyword(keyword: String) {
        currentPage = 0
        _currentKeyword.update { keyword }

        setState {
            copy(
                companyList = listOf(),
                mainLoadState = LoadState.Success
            )
        }
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

            is UiEvent.Refresh -> searchCompanyByKeyword(isLoadMore = false)

            is UiEvent.LoadMore -> searchCompanyByKeyword(isLoadMore = true)
        }
    }
}