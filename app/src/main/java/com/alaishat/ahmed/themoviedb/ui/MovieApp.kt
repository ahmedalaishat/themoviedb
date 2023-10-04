package com.alaishat.ahmed.themoviedb.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.ui.navigation.BottomBarDestination
import com.alaishat.ahmed.themoviedb.ui.navigation.MovieNavHost
import com.alaishat.ahmed.themoviedb.ui.component.ConnectionSnackBar
import com.alaishat.ahmed.themoviedb.ui.component.MovieBottomBar
import com.alaishat.ahmed.themoviedb.ui.component.SnackbarVisualsCustom

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieApp(
    connectionDataSource: ConnectionDataSource,
    isInitError: Boolean,
    appState: MovieAppState = rememberMovieAppState(
        connectionDataSource = connectionDataSource,
    )
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        val snackbarHostState = remember { SnackbarHostState() }

        val connectionState by appState.connectionState.collectAsStateWithLifecycle()
        val noConnectionMessage = stringResource(R.string.no_connection)
        val backOnlineMessage = stringResource(R.string.back_online)
        LaunchedEffect(connectionState) {
            when (connectionState) {
                ConnectionState.Offline ->
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = noConnectionMessage,
                            isOnline = false,
                            duration = SnackbarDuration.Indefinite,
                        )
                    )

                ConnectionState.BackOnline ->
                    snackbarHostState.showSnackbar(
                        SnackbarVisualsCustom(
                            message = backOnlineMessage,
                            isOnline = true,
                            duration = SnackbarDuration.Short,
                        )
                    )

                else -> {}
            }
        }

        Scaffold(
            bottomBar = {
                MovieBottomBar(
                    destinations = remember { BottomBarDestination.values().toList() },
                    selectedDestination = BottomBarDestination.getByRoute(appState.currentDestination?.route),
                    onNavigateToDestination = { appState.navController.navigate(it.route) }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) {
                    ConnectionSnackBar(it)
                }
            },
        ) { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                if (isInitError) Text(text = stringResource(id = R.string.something_went_wrong))
                else
                MovieNavHost(
                    navController = appState.navController,
                )
            }
        }
    }
}