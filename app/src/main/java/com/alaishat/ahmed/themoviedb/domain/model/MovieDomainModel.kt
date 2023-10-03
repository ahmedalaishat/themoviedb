package com.alaishat.ahmed.themoviedb.domain.model

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
data class MovieDomainModel(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: String,
)