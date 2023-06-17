package com.alaishat.ahmed.themoviedb.feature.movie.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alaishat.ahmed.themoviedb.feature.movie.MovieScreen

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
const val movieRoute = "movie_route"

fun NavController.navigateToMovie(navOptions: NavOptions? = null) {
    this.navigate(movieRoute, navOptions)
}

fun NavGraphBuilder.movieScreen() {
    composable(route = movieRoute) {
        MovieScreen()
    }
}
