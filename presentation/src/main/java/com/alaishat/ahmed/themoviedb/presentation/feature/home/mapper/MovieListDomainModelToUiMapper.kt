package com.alaishat.ahmed.themoviedb.presentation.feature.home.mapper

import com.alaishat.ahmed.themoviedb.presentation.architecture.result.Result
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.MovieListUiState

/**
 * Created by Ahmed Al-Aishat on Oct/04/2023.
 * The Movie DB Project.
 */
fun Result<List<Movie>>.toMovieListViewState(): MovieListUiState {
    return when (this) {
        is Result.Loading -> MovieListUiState.Loading
        is Result.Error -> MovieListUiState.Error(this.message)
        is Result.Success -> if (this.data.isEmpty()) MovieListUiState.NoCache else MovieListUiState.Success(this.data)
    }
}