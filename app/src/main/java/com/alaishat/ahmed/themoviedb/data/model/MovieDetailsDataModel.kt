 package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.MovieDetailsDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
data class MovieDetailsDataModel(
    val backdropPath: String?,
    val genreDataModels: List<GenreDataModel>,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,//AHMED_TODO: convert me to date
    val runtime: Int,
    val title: String,
    val voteAverage: Float,
)


fun MovieDetailsDataModel.toMoviesDetailsDomainModel() = MovieDetailsDomainModel(
    id = id,
    overview = overview,
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    releaseYear = releaseDate.split("-").first(),
    runtime = "${runtime / 60}h ${runtime % 60}m",
    title = title,
    voteAverage = "%.1f".format(voteAverage),
    genre = genreDataModels.firstOrNull()?.name,
    watchlist = false
)