package com.alaishat.ahmed.themoviedb.feature.movie.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alaishat.ahmed.themoviedb.feature.movie.MovieRoute

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
const val movieIdArg = "movie_id"

class MovieDetailsArgs(val movieId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(movieId = checkNotNull(savedStateHandle.get<Int>(movieIdArg)))
}


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
