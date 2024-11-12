package com.example.companysearchapp.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.companysearchapp.util.Route

@Composable
internal fun NavHostScreen(
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Main
    ) {
        composable<Route.Main> {
            MainRoute(
                onClickCompanyItem = { company ->
                    navController.navigate(
                        Route.Detail(
                            companyId = company.id
                        )
                    )
                }
            )
        }
        composable<Route.Detail> {
            DetailRoute(
                onClickLinkItem = { url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            )
        }
    }
}