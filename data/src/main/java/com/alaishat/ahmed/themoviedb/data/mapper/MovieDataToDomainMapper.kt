package com.alaishat.ahmed.themoviedb.data.mapper

import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel

/**
 * Created by Ahmed Al-Aishat on Feb/08/2024.
 * The Movie DB Project.
 */
fun MovieDataModel.toDomainModel() = MovieDomainModel(
    id = id,
    overview = overview,
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    releaseDate = releaseDate,
    title = title,
    voteAverage = "%.1f".format(voteAverage),
)

fun List<MovieDataModel>.mapToMovies() = map(MovieDataModel::toDomainModel)