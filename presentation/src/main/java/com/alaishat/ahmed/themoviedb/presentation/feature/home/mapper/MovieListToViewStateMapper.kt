package com.alaishat.ahmed.themoviedb.presentation.feature.home.mapper

import com.alaishat.ahmed.themoviedb.presentation.architecture.result.Result
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.MovieListViewState

/**
 * Created by Ahmed Al-Aishat on Oct/04/2023.
 * The Movie DB Project.
 */
fun Result<List<Movie>>.toMovieListViewState(): MovieListViewState {
    return when (this) {
        is Result.Loading -> MovieListViewState.Loading
        is Result.Error -> MovieListViewState.Error(this.message)
        is Result.Success -> MovieListViewState.Success(this.data)
    }
}