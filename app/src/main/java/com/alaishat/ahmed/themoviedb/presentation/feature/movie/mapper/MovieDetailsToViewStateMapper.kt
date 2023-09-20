package com.alaishat.ahmed.themoviedb.presentation.feature.movie.mapper

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.MovieDetailsViewState

/**
 * Created by Ahmed Al-Aishat on Sep/21/2023.
 * The Movie DB Project.
 */
fun MovieDetailsDomainModel.toViewState() = when (this) {
    is MovieDetailsDomainModel.Success -> MovieDetailsViewState.Success(
        id = id,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseYear = releaseYear,
        title = title,
        voteAverage = voteAverage,
        genre = genre,
        runtime = runtime,
        watchlist = watchlist,
    )

    MovieDetailsDomainModel.Disconnected -> MovieDetailsViewState.Disconnected
    is MovieDetailsDomainModel.Error -> MovieDetailsViewState.Error(exception.toViewState())
    MovieDetailsDomainModel.Loading -> MovieDetailsViewState.Loading
}