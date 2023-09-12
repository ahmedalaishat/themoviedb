package com.alaishat.ahmed.themoviedb.domain.model

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
data class MovieDetailsDomainModel(
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
)