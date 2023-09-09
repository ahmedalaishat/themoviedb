package com.alaishat.ahmed.themoviedb.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alaishat.ahmed.themoviedb.feature.home.HomeRoute

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onMovieClick: (movieId: Int) -> Unit,
) {
    composable(route = homeRoute) {
        HomeRoute(
            onMovieClick = onMovieClick,
        )
    }
}
