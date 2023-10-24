package com.alaishat.ahmed.themoviedb.presentation.feature.movie.model

/**
 * Created by Ahmed Al-Aishat on Sep/21/2023.
 * The Movie DB Project.
 */
sealed interface MovieDetailsViewState {
    data class Success(
        val id: Int,
        val overview: String,
        val posterPath: String,
        val backdropPath: String,
        val releaseYear: String,
        val title: String,
        val voteAverage: String,
        val genre: String?,
        val runtime: String,
        val watchlist: Boolean,
    ) : MovieDetailsViewState

    object Loading : MovieDetailsViewState
    object Disconnected : MovieDetailsViewState
    data class Error(val error: MovieDetailsError) : MovieDetailsViewState
}