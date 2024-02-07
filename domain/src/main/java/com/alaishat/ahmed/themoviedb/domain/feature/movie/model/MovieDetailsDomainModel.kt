package com.alaishat.ahmed.themoviedb.domain.feature.movie.model

import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
sealed interface MovieDetailsDomainModel {
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
    ) : MovieDetailsDomainModel

    data object Disconnected : MovieDetailsDomainModel

    data class Error(val exception: DomainException) : MovieDetailsDomainModel

    data object Loading : MovieDetailsDomainModel
}