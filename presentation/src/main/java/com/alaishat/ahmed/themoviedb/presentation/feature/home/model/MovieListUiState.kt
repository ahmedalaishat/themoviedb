package com.alaishat.ahmed.themoviedb.presentation.feature.home.model

import com.alaishat.ahmed.themoviedb.presentation.architecture.usermessage.UserMessage
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie

/**
 * Created by Ahmed Al-Aishat on Oct/04/2023.
 * The Movie DB Project.
 */
sealed interface MovieListUiState {
    data object Loading : MovieListUiState
    data object NoCache : MovieListUiState

    data class Error(
        val message: UserMessage,
    ) : MovieListUiState

    data class Success(
        val movies: List<Movie>,
    ) : MovieListUiState
}