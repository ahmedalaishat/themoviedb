package com.alaishat.ahmed.themoviedb.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.presentation.common.model.ConnectionStateUiModel
import com.alaishat.ahmed.themoviedb.presentation.common.model.MainActivityUiState
import com.alaishat.ahmed.themoviedb.ui.common.EmptyContent
import com.alaishat.ahmed.themoviedb.ui.component.ConnectionSnackBar
import com.alaishat.ahmed.themoviedb.ui.component.MovieBottomBar
import com.alaishat.ahmed.themoviedb.ui.component.SnackbarVisualsCustom
import com.alaishat.ahmed.themoviedb.ui.navigation.BottomBarDestination
import com.alaishat.ahmed.themoviedb.ui.navigation.MovieNavHost
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieApp(
    appState: MovieAppState,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        val snackbarHostState = remember { SnackbarHostState() }

        if (appState.mainActivityUiState == MainActivityUiState.NoCache) {
            EmptyContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimensions.ScreenPadding),
                imageId = R.drawable.no_connection,
                title = stringResource(id = R.string.no_connection),
                subtitle = stringResource(id = R.string.initial_setup_error)
            )
        } else {
            Scaffold(
                bottomBar = {
                    MovieBottomBar(
                        destinations = remember { BottomBarDestination.entries },
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
                    when (appState.mainActivityUiState) {
                        MainActivityUiState.Loading -> {}
                        MainActivityUiState.NoCache -> {}
                        is MainActivityUiState.Success -> {
                            MovieNavHost(
                                navController = appState.navController,
                            )
                            ConnectionStateSnackbar(
                                snackbarHostState = snackbarHostState,
                                connectionState = appState.mainActivityUiState.connectionState
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ConnectionStateSnackbar(
    snackbarHostState: SnackbarHostState,
    connectionState: ConnectionStateUiModel,
) {
    val noConnectionMessage = stringResource(R.string.no_connection)
    val backOnlineMessage = stringResource(R.string.back_online)
    LaunchedEffect(connectionState) {
        when (connectionState) {
            ConnectionStateUiModel.Offline ->
                snackbarHostState.showSnackbar(
                    SnackbarVisualsCustom(
                        message = noConnectionMessage,
                        isOnline = false,
                        duration = SnackbarDuration.Indefinite,
                    )
                )

            ConnectionStateUiModel.BackOnline ->
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
}