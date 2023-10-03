package com.alaishat.ahmed.themoviedb.datasource.impl.movie.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Serializable
data class MovieListRes(
    @SerialName("results") val results: List<NetworkMovie>
)