package com.example.companysearchapp.ui.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.util.COMPANY_INFO_TYPE


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CompanyListItem(
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
                contentType = { COMPANY_INFO_TYPE },
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