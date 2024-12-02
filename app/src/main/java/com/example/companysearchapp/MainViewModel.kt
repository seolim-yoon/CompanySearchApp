package com.example.companysearchapp

import com.example.companysearchapp.base.BaseViewModel
import com.example.companysearchapp.base.UiState
import com.example.companysearchapp.mapper.CompanyUiModelMapper
import com.example.companysearchapp.ui.event.UiEvent
import com.example.companysearchapp.ui.state.LoadState
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.util.PAGE_SIZE
import com.example.companysearchapp.util.SEARCH_TIME_DELAY
import com.example.domain.entity.CompanyListEntity
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
import kotlinx.coroutines.flow.flow
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
            flow {
                setState {
                    copy(mainLoadState = LoadState.Loading)
                }
                val result = searchCompanyUseCase(
                    keyword = keyword,
                    offset = 0,
                    limit = PAGE_SIZE
                )
                emit(result)
            }
        }

    init {
        searchCompanyByKeyword()
    }

    override fun createInitialState(): MainUiState = MainUiState()

    private fun searchCompanyByKeyword() {
        viewModelLaunch(onSuccess = {
            searchResult.collect { result ->
                updateCompanyList(result)
            }
        })
    }

    private fun loadMoreCompany() {
        if (isLoadingPaging) return

        viewModelLaunch(onSuccess = {
            isLoadingPaging = true

            val entityResult = searchCompanyUseCase(
                keyword = _currentKeyword.value,
                offset = (++currentPage) * PAGE_SIZE,
                limit = PAGE_SIZE
            )
            updateCompanyList(entityResult)
            isLoadingPaging = false
        })
    }


    private fun updateCompanyList(result: CompanyListEntity) {
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
    }

    private fun inputSearchKeyword(keyword: String) {
        currentPage = 0
        _currentKeyword.update { keyword }

        setState {
            copy(
                companyList = listOf()
            )
        }
    }

    override fun handleException(throwable: Throwable) {
        setState {
            copy(
                mainLoadState = LoadState.Error(throwable)
            )
        }
        isLoadingPaging = false
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.InputKeyword -> inputSearchKeyword(event.keyword)

            is UiEvent.Refresh -> searchCompanyByKeyword()

            is UiEvent.LoadMore -> loadMoreCompany()
        }
    }
}