package com.example.companysearchapp

import com.example.companysearchapp.base.BaseViewModel
import com.example.companysearchapp.base.UiState
import com.example.companysearchapp.mapper.CompanyUiModelMapper
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.util.PAGE_SIZE
import com.example.domain.usecase.SearchCompanyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private var currentKeyword = ""

    override fun createInitialState(): MainUiState = MainUiState()

    private fun searchCompanyByKeyword(
        keyword: String
    ) {
        setState {
            copy(mainLoadState = LoadState.Loading)
        }
        if (keyword.isNotEmpty()) {
            viewModelLaunch(onSuccess = {
                currentPage = 0
                currentKeyword = keyword

                val searchResult = companyUiModelMapper.mapToCompanyListUiModel(
                    searchCompanyUseCase(
                        keyword = keyword,
                        offset = currentPage * PAGE_SIZE,
                        limit = PAGE_SIZE
                    )
                )

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
            })
        } else {
            setState {
                copy(
                    mainLoadState = LoadState.Success(
                        emptyList()
                    )
                )
            }
        }
    }

    private fun loadMoreCompanyList(page: Int) {
        viewModelLaunch(onSuccess = {

            val loadMoreResult = companyUiModelMapper.mapToCompanyListUiModel(
                searchCompanyUseCase(
                    keyword = currentKeyword,
                    offset = page * PAGE_SIZE,
                    limit = PAGE_SIZE
                )
            )

            if (loadMoreResult.companyList.isNotEmpty()) {
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
            is UiEvent.SearchCompany -> searchCompanyByKeyword(keyword = event.keyword)

            is UiEvent.Refresh -> {}

            is UiEvent.LoadMore -> loadMoreCompanyList(++currentPage)

            is UiEvent.RequestDetailInfo -> {}
        }
    }
}