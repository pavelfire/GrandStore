package com.vk.directop.grandstore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vk.directop.grandstore.applist.AppListRoute
import com.vk.directop.grandstore.detailcard.DetailRoute

@Composable
fun GrandStoreNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppListDestination,
        modifier = modifier,
    ) {
        composable<AppListDestination> {
            AppListRoute(
                onOpenDetail = { gameId ->
                    navController.navigate(DetailDestination(gameId))
                }
            )
        }
        composable<DetailDestination> {
            DetailRoute(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
