package com.example.companysearchapp

import com.example.companysearchapp.base.BaseViewModel
import com.example.companysearchapp.mapper.CompanyUiModelMapper
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.util.PAGE_SIZE
import com.example.domain.usecase.SearchCompanyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


sealed interface MainLoadState {
    data object Loading : MainLoadState
    data class Success(val companyList: List<CompanyUiModel>) : MainLoadState
    data class Error(val error: Throwable) : MainLoadState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchCompanyUseCase: SearchCompanyUseCase,
    private val companyUiModelMapper: CompanyUiModelMapper
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<MainLoadState> =
        MutableStateFlow(MainLoadState.Success(companyList = listOf()))
    val uiState = _uiState.asStateFlow()

    private var currentPage = 0
    private var currentKeyword = ""

    init {
    }

    private fun searchCompanyByKeyword(
        keyword: String
    ) {
        _uiState.update {
            MainLoadState.Loading
        }

        viewModelLaunch(onSuccess = {
            currentPage = 0
            currentKeyword = keyword

            val searchResult = companyUiModelMapper.mapToCompanyListUiModel(
                searchCompanyUseCase(
                    keyword = keyword,
                    offset = currentPage * PAGE_SIZE,
                    limit = PAGE_SIZE
                )
            ).companyList

            _uiState.update {
                MainLoadState.Success(
                    companyList = mutableListOf<CompanyUiModel>().apply {
                        addAll(searchResult)
                    }
                )
            }
        })
    }

    private fun loadMoreCompanyList(page: Int) {
        viewModelLaunch(onSuccess = {
            val moreCompanyList = companyUiModelMapper.mapToCompanyListUiModel(
                searchCompanyUseCase(
                    keyword = currentKeyword,
                    offset = page * PAGE_SIZE,
                    limit = PAGE_SIZE
                )
            ).companyList

            if (moreCompanyList.isNotEmpty()) {
                _uiState.update {
                    if (it is MainLoadState.Success) {
                        MainLoadState.Success(
                            companyList = it.companyList.toMutableList().apply {
                                addAll(moreCompanyList)
                            }
                        )

                    } else it
                }
            }
        })
    }

    override fun handleException(throwable: Throwable) {
        _uiState.update {
            MainLoadState.Error(throwable)
        }
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SearchCompany -> searchCompanyByKeyword(keyword = event.keyword)

            is UiEvent.Refresh -> {}

            is UiEvent.LoadMore -> loadMoreCompanyList(++currentPage)

            is UiEvent.RequestDetailInfo -> {}
        }
    }
}