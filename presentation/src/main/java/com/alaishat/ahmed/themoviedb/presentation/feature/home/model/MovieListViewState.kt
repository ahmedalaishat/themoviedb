package com.alaishat.ahmed.themoviedb.presentation.feature.home.model

import com.alaishat.ahmed.themoviedb.presentation.architecture.usermessage.UserMessage
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie

/**
 * Created by Ahmed Al-Aishat on Oct/04/2023.
 * The Movie DB Project.
 */
sealed interface MovieListViewState {
    object Loading : MovieListViewState

    data class Error(
        val message: UserMessage,
    ) : MovieListViewState

    data class Success(
        val movies: List<Movie>,
    ) : MovieListViewState
}