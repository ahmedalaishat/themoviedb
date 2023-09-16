package com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper

import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieEntity

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
fun MovieEntity.toMovieDataModel() = MovieDataModel(
    backdropPath = backdropPath,
    id = movieId.toInt(),
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage.toFloat(),
)

fun MovieDataModel.toEntity() = MovieEntity(
    movieId = id.toLong(),
    title = title,
    backdropPath = backdropPath,
    posterPath = posterPath,
    overview = overview,
    voteAverage = voteAverage.toDouble(),
    releaseDate = releaseDate,
)