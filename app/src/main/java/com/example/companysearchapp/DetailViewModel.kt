package com.example.companysearchapp

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.companysearchapp.base.BaseViewModel
import com.example.companysearchapp.base.UiState
import com.example.companysearchapp.mapper.CompanyUiModelMapper
import com.example.companysearchapp.ui.event.UiEvent
import com.example.companysearchapp.ui.state.LoadState
import com.example.companysearchapp.uimodel.DetailCompanyUiModel
import com.example.companysearchapp.util.Route
import com.example.domain.usecase.GetCompanyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class DetailUiState(
    val detailCompany: DetailCompanyUiModel = DetailCompanyUiModel(),
    val detailLoadState: LoadState = LoadState.Loading
): UiState

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCompanyDetailUseCase: GetCompanyDetailUseCase,
    private val companyUiModelMapper: CompanyUiModelMapper
): BaseViewModel<DetailUiState>() {
    private var currentCompanyId = 0

    init {
        getCompanyDetailInfo(
            companyId = savedStateHandle.toRoute<Route.Detail>().companyId
        )
    }

    override fun createInitialState(): DetailUiState = DetailUiState()

    private fun getCompanyDetailInfo(companyId: Int) {
        setState {
            copy(detailLoadState = LoadState.Loading)
        }
        viewModelLaunch(onSuccess = {
            currentCompanyId = companyId
            val detailInfo = companyUiModelMapper.mapToDetailCompanyUiModel(
                getCompanyDetailUseCase(
                    companyId = companyId
                ).company
            )

            setState {
                copy(
                    detailCompany = detailInfo,
                    detailLoadState = LoadState.Success
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
            is UiEvent.InputKeyword -> {}

            is UiEvent.Refresh -> getCompanyDetailInfo(currentCompanyId)

            is UiEvent.LoadMore -> {}
        }
    }
}