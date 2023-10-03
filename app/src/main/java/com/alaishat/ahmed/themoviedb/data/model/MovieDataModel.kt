package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
data class MovieDataModel(
    val backdropPath: String?,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,//AHMED_TODO: convert me to date
    val title: String,
    val voteAverage: Float,
)

fun MovieDataModel.toMovieDomainModel() = MovieDomainModel(
    id = id,
    overview = overview,
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    releaseDate = releaseDate,
    title = title,
    voteAverage = "%.1f".format(voteAverage),
)

fun List<MovieDataModel>.mapToMovies() = map(MovieDataModel::toMovieDomainModel)