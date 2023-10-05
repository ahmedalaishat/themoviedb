package com.alaishat.ahmed.themoviedb.ui.feature.movie.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.movieIdArg
import com.alaishat.ahmed.themoviedb.ui.feature.movie.MovieRoute

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
const val movieRoute = "movie_route"

fun NavController.navigateToMovie(
    movieId: Int,
    navOptions: NavOptions? = null,
) {
    this.navigate("$movieRoute/$movieId", navOptions)
}

fun NavGraphBuilder.movieScreen() {
    composable(
        route = "$movieRoute/{$movieIdArg}",
        arguments = listOf(
            navArgument(movieIdArg) {
                type = NavType.IntType
            }
        ),
    ) {
        MovieRoute()
    }
}
