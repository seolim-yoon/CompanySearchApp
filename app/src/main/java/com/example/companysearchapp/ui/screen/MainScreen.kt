package com.example.companysearchapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.companysearchapp.LoadState
import com.example.companysearchapp.MainUiState
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.item.CompanyItem
import com.example.companysearchapp.ui.item.SearchBarItem
import com.example.companysearchapp.uimodel.CompanyUiModel

@Composable
internal fun MainScreen(
    uiState: MainUiState,
    onSearchCompany: (String) -> Unit,
    onClickCompanyItem: (CompanyUiModel) -> Unit,
    loadMoreItem: () -> Unit,
    onRefresh: () -> Unit
) {
    var keyword by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp)),
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchBarItem(
                keyword = keyword,
                onValueChange = {
                    keyword = it
                    onSearchCompany(keyword)
                },
                onClickClearBtn = {
                    keyword = ""
                    onSearchCompany(keyword)
                }
            )

            CompanyList(
                uiState = uiState,
                onClickCompanyItem = onClickCompanyItem,
                loadMoreItem = {
                    loadMoreItem()
                },
                onRefresh = onRefresh
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CompanyList(
    uiState: MainUiState,
    onClickCompanyItem: (CompanyUiModel) -> Unit,
    loadMoreItem: () -> Unit,
    onRefresh: () -> Unit
) {
    val listState = rememberLazyListState()
    val needLoadMore by remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex != 0 && lastVisibleItemIndex >= totalItemsCount - 1
        }
    }

    LaunchedEffect(needLoadMore) {
        if (needLoadMore) {
            loadMoreItem()
        }
    }

    when (uiState.mainLoadState) {
        is LoadState.Loading -> {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
        is LoadState.Success -> {
            CompositionLocalProvider(LocalOverscrollConfiguration provides null)  {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(dimensionResource(R.dimen.padding_16dp)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp)),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        key = { it.id },
                        items = uiState.mainLoadState.data
                    ) { company ->
                        CompanyItem(
                            company = company,
                            onClickCompanyItem = onClickCompanyItem
                        )
                    }
                }
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