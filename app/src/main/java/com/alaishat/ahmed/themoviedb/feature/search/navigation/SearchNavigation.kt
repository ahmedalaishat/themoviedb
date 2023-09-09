package com.alaishat.ahmed.themoviedb.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alaishat.ahmed.themoviedb.feature.search.SearchRoute

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
const val searchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onMovieClick: (movieId: Int) -> Unit,
) {
    composable(route = searchRoute) {
        SearchRoute(
            onMovieClick = onMovieClick,
        )
    }
}
