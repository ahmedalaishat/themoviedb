package com.alaishat.ahmed.themoviedb.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.connection.model.ConnectionStateDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Created by Ahmed Al-Aishat on Oct/03/2023.
 * The Movie DB Project.
 */
@Composable
fun rememberMovieAppState(
    connectionDataSource: ConnectionDataSource,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): MovieAppState {

    return remember(navController, coroutineScope, connectionDataSource) {
        MovieAppState(navController, coroutineScope, connectionDataSource)
    }
}

@Stable
class MovieAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val connectionDataSource: ConnectionDataSource,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val connectionState = connectionDataSource.observeIsConnected()
        .map {
            when {
                it is ConnectionStateDataModel.Connected && it.backOnline -> ConnectionState.BackOnline

                it == ConnectionStateDataModel.Disconnected -> ConnectionState.Offline
                else -> ConnectionState.Online
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConnectionState.Online,
        )
}

sealed interface ConnectionState {
    object Offline : ConnectionState
    object BackOnline : ConnectionState
    object Online : ConnectionState
}