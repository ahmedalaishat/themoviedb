package com.alaishat.ahmed.themoviedb.datasource.impl.movie.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class MovieReviewsRes(
    @SerialName("results") val reviews: List<NetworkReviewModel>
)
