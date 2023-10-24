package com.alaishat.ahmed.themoviedb.datasource.movie.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class MovieRatingReq(
    @SerialName("value") val value: Int
)