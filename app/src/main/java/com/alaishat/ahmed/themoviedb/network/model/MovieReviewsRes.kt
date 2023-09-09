package com.alaishat.ahmed.themoviedb.network.model

import com.alaishat.ahmed.themoviedb.data.model.NetworkReview
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class MovieReviewsRes(
    @SerialName("results") val reviews: List<NetworkReview>
)
