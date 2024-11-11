package com.example.companysearchapp

import com.example.companysearchapp.base.BaseViewModel
import com.example.companysearchapp.base.UiState
import com.example.companysearchapp.mapper.CompanyUiModelMapper
import com.example.domain.usecase.GetCompanyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class DetailUiState(
    val detailLoadState: DetailLoadState = LoadState.Loading
): UiState

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCompanyDetailUseCase: GetCompanyDetailUseCase,
    private val companyUiModelMapper: CompanyUiModelMapper
): BaseViewModel<DetailUiState>() {
    override fun createInitialState(): DetailUiState = DetailUiState()

    private fun getCompanyDetailInfo(companyId: Int) {
        setState {
            copy(detailLoadState = LoadState.Loading)
        }
        viewModelLaunch(onSuccess = {
            val detailInfo = companyUiModelMapper.mapToDetailCompanyUiModel(
                getCompanyDetailUseCase(
                    companyId = companyId
                ).company
            )

            setState {
                copy(
                    detailLoadState =   LoadState.Success(
                        detailInfo
                    )
                )
            }
        })
    }

    override fun handleException(throwable: Throwable) {
        setState {
            copy(
                detailLoadState =  LoadState.Error(throwable)
            )
        }
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SearchCompany -> {}

            is UiEvent.Refresh -> {}

            is UiEvent.LoadMore -> {}

            is UiEvent.RequestDetailInfo -> getCompanyDetailInfo(event.companyId)
        }
    }
}