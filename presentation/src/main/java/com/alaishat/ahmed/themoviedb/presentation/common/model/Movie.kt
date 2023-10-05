package com.alaishat.ahmed.themoviedb.presentation.common.model

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/27/2023.
 * The Movie DB Project.
 */
data class Movie(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: String,
)

fun MovieDomainModel.toPresentation() = Movie(
    id = id,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
)