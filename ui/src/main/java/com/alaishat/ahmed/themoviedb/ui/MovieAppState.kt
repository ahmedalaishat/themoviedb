package com.alaishat.ahmed.themoviedb.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alaishat.ahmed.themoviedb.presentation.common.model.MainActivityUiState

/**
 * Created by Ahmed Al-Aishat on Oct/03/2023.
 * The Movie DB Project.
 */
@Composable
fun rememberMovieAppState(
    mainActivityUiState: MainActivityUiState,
    navController: NavHostController = rememberNavController(),
): MovieAppState {

    return remember(mainActivityUiState, navController) {
        MovieAppState(mainActivityUiState, navController)
    }
}

@Stable
class MovieAppState(
    val mainActivityUiState: MainActivityUiState,
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
}