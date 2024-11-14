package com.example.companysearchapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.companysearchapp.ui.state.LoadState
import com.example.companysearchapp.MainUiState
import com.example.companysearchapp.MainViewModel
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.event.UiEvent
import com.example.companysearchapp.ui.item.CompanyListItem
import com.example.companysearchapp.ui.item.EmptyScreen
import com.example.companysearchapp.ui.item.SearchBarItem
import com.example.companysearchapp.uimodel.CompanyUiModel

@Composable
internal fun MainRoute(
    mainViewModel: MainViewModel = hiltViewModel(),
    onClickCompanyItem: (CompanyUiModel) -> Unit,
) {
    val mainUiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val keyword by mainViewModel.currentKeyword.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp)),
            modifier = Modifier.padding(innerPadding)
        ) {
            MainScreen(
                uiState = mainUiState,
                keyword = keyword,
                onValueChange = { mainViewModel.onEvent(UiEvent.InputKeyword(it)) },
                onClickClearBtn = { mainViewModel.onEvent(UiEvent.InputKeyword("")) },
                onClickCompanyItem = onClickCompanyItem,
                loadMoreItem = { mainViewModel.onEvent(UiEvent.LoadMore) },
                onRefresh = { mainViewModel.onEvent(UiEvent.Refresh) }
            )
        }
    }
}

@Composable
internal fun MainScreen(
    uiState: MainUiState,
    keyword: String,
    onValueChange: (String) -> Unit,
    onClickClearBtn: () -> Unit,
    onClickCompanyItem: (CompanyUiModel) -> Unit,
    loadMoreItem: () -> Unit,
    onRefresh: () -> Unit
) {
    SearchBarItem(
        keyword = keyword,
        onValueChange = onValueChange,
        onClickClearBtn = onClickClearBtn
    )

    when (uiState.mainLoadState) {
        is LoadState.Loading -> {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
        is LoadState.Success -> {
            if (uiState.mainLoadState.data.isNotEmpty()) {
                CompanyListItem(
                    isEnd = uiState.isEnd,
                    companyList = uiState.mainLoadState.data,
                    onClickCompanyItem = onClickCompanyItem,
                    loadMoreItem = loadMoreItem
                )
            } else {
                EmptyScreen(
                    keyword = keyword
                )
            }
        }
        is LoadState.Error -> {
            ErrorScreen(
                errorMessage = uiState.mainLoadState.error.message.toString(),
                onRefresh = onRefresh
            )
        }
    }
}