package com.example.companysearchapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.companysearchapp.MainUiState
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.item.SearchBarItem
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.util.SEARCH_TIME_DELAY
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
internal fun MainScreen(
    uiState: MainUiState,
    onSearchCompany: (String) -> Unit,
    onClickCompanyItem: (CompanyUiModel) -> Unit,
    loadMoreItem: () -> Unit,
    onRefresh: () -> Unit
) {
    var keyword by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        snapshotFlow { keyword }
            .debounce(SEARCH_TIME_DELAY)
            .distinctUntilChanged()
            .collect {
                onSearchCompany(it)
            }
    }

    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp)),
            modifier = Modifier.padding(innerPadding)
        ) {
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

            CompanyScreen(
                uiState = uiState,
                keyword = keyword,
                onClickCompanyItem = onClickCompanyItem,
                loadMoreItem = loadMoreItem,
                onRefresh = onRefresh
            )
        }
    }
}
