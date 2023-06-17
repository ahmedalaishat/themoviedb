package com.alaishat.ahmed.themoviedb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.alaishat.ahmed.themoviedb.feature.home.navigation.homeRoute
import com.alaishat.ahmed.themoviedb.feature.home.navigation.homeScreen
import com.alaishat.ahmed.themoviedb.feature.movie.navigation.movieScreen
import com.alaishat.ahmed.themoviedb.feature.search.navigation.searchScreen
import com.alaishat.ahmed.themoviedb.feature.watchlist.navigation.watchListScreen

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen()
        searchScreen()
        watchListScreen()
        movieScreen()
    }
}