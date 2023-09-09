package com.alaishat.ahmed.themoviedb.feature.watchlist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alaishat.ahmed.themoviedb.feature.watchlist.WatchListRoute

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
const val watchListRoute = "watch_list_route"

fun NavController.navigateToWatchList(navOptions: NavOptions? = null) {
    this.navigate(watchListRoute, navOptions)
}

fun NavGraphBuilder.watchListScreen(
    onMovieClick: (movieId: Int) -> Unit,
) {
    composable(route = watchListRoute) {
        WatchListRoute(
            onMovieClick = onMovieClick,
        )
    }
}