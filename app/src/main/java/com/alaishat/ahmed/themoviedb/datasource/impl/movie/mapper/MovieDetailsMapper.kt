package com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper

import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieDetailsEntity
import comalaishatahmedthemoviedbdatasourceimplsqldelight.SelectMovieWithDetailsById

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
fun MovieDetailsDataModel.toEntity() = MovieDetailsEntity(
    movieId = id.toLong(),
    runtime = runtime.toLong(),
)

fun List<SelectMovieWithDetailsById>.toMovieDetailsDataModel() = with(firstOrNull()) {
    return@with if (this == null) null
    else MovieDetailsDataModel(
        backdropPath = backdropPath,
        genreDataModels = map {
            GenreDataModel(id = genreId.toInt(), name = name)
        },
        id = movieId.toInt(),
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime.toInt(),
        title = title,
        voteAverage = voteAverage.toFloat(),
    )
}