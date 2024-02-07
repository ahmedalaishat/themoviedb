package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel

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