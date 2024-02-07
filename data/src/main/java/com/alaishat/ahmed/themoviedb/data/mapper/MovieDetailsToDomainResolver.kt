package com.alaishat.ahmed.themoviedb.data.mapper

import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
class MovieDetailsToDomainResolver {

    suspend fun toDomain(
        connectionState: ConnectionStateDomainModel,
        remoteMovieProvider: suspend () -> MovieDetailsDataModel?,
        localMovieProvider: suspend () -> MovieDetailsDataModel?,
    ) = when (connectionState) {
        is ConnectionStateDomainModel.Connected -> remoteMovieProvider()?.toMoviesDetailsDomainModel()
            ?: localMovieProvider()?.toMoviesDetailsDomainModel()

        else -> localMovieProvider()?.toMoviesDetailsDomainModel()
    } ?: MovieDetailsDomainModel.Disconnected
}

fun MovieDetailsDataModel.toMoviesDetailsDomainModel() = MovieDetailsDomainModel.Success(
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