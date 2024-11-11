package com.example.companysearchapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.companysearchapp.LoadState
import com.example.companysearchapp.MainUiState
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.item.CompanyItem
import com.example.companysearchapp.ui.item.EmptyScreen
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme
import com.example.companysearchapp.uimodel.CompanyUiModel


@Composable
internal fun CompanyScreen(
    uiState: MainUiState,
    keyword: String,
    onClickCompanyItem: (CompanyUiModel) -> Unit,
    loadMoreItem: () -> Unit,
    onRefresh: () -> Unit
) {
    when (uiState.mainLoadState) {
        is LoadState.Loading -> {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
        is LoadState.Success -> {
            if (uiState.mainLoadState.data.isNotEmpty()) {
                CompanyList(
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CompanyList(
    isEnd: Boolean,
    companyList: List<CompanyUiModel>,
    onClickCompanyItem: (CompanyUiModel) -> Unit,
    loadMoreItem: () -> Unit
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
        if (needLoadMore && !isEnd) {
            loadMoreItem()
        }
    }

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_16dp)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp)),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                key = { it.id },
                items = companyList
            ) { company ->
                CompanyItem(
                    company = company,
                    onClickCompanyItem = onClickCompanyItem
                )
                HorizontalDivider(
                    color = CompanySearchAppTheme.colors.gray,
                    thickness = 1.dp
                )
            }
        }
    }
}