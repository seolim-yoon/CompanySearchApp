package com.example.companysearchapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.companysearchapp.DetailViewModel
import com.example.companysearchapp.MainViewModel
import com.example.companysearchapp.UiEvent
import com.example.companysearchapp.util.ScreenType

@Composable
internal fun NavHostScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val mainUiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val detailUiState by detailViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = ScreenType.MainScreen
    ) {
        composable<ScreenType.MainScreen> {
            MainScreen(
                uiState = mainUiState,
                onSearchCompany = { keyword ->
                    mainViewModel.onEvent(UiEvent.SearchCompany(keyword))
                },
                onClickCompanyItem = { company ->
                    detailViewModel.onEvent(UiEvent.RequestDetailInfo(companyId = company.id))
                    navController.navigate(
                        ScreenType.DetailScreen(
                            companyId = company.id
                        )
                    )
                },
                loadMoreItem = { mainViewModel.onEvent(UiEvent.LoadMore) },
                onRefresh = { } // TODO : refresh
            )
        }
        composable<ScreenType.DetailScreen> {
            DetailScreen(
                uiState = detailUiState,
                onRefresh = { } // TODO : refresh
            )
        }
    }
}