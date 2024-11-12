package com.example.companysearchapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.companysearchapp.LoadState
import com.example.companysearchapp.MainUiState
import com.example.companysearchapp.MainViewModel
import com.example.companysearchapp.R
import com.example.companysearchapp.UiEvent
import com.example.companysearchapp.ui.item.CompanyListItem
import com.example.companysearchapp.ui.item.EmptyScreen
import com.example.companysearchapp.ui.item.SearchBarItem
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.util.SEARCH_TIME_DELAY
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
internal fun MainRoute(
    mainViewModel: MainViewModel = hiltViewModel(),
    onClickCompanyItem: (CompanyUiModel) -> Unit,
) {
    val mainUiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp)),
            modifier = Modifier.padding(innerPadding)
        ) {
            MainScreen(
                uiState = mainUiState,
                onSearchCompany = { keyword -> mainViewModel.onEvent(UiEvent.SearchCompany(keyword = keyword)) },
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
    onSearchCompany: (String) -> Unit,
    onClickCompanyItem: (CompanyUiModel) -> Unit,
    loadMoreItem: () -> Unit,
    onRefresh: () -> Unit
) {
    var keyword by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        snapshotFlow { keyword }
            .debounce(SEARCH_TIME_DELAY)
            .distinctUntilChanged()
            .collect { keyword ->
                onSearchCompany(keyword)
            }
    }

    SearchBarItem(
        keyword = keyword,
        onValueChange = {
            keyword = it
        },
        onClickClearBtn = {
            keyword = ""
            onSearchCompany(keyword)
        }
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