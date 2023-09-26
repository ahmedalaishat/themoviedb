package com.alaishat.ahmed.themoviedb.domain.feature.home

import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/24/2023.
 * The Movie DB Project.
 */
sealed interface TopMoviesDomainModel {

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
    ) : TopMoviesDomainModel

    object Disconnected : TopMoviesDomainModel

    data class Error(val exception: DomainException) : TopMoviesDomainModel

    object Loading : TopMoviesDomainModel
}