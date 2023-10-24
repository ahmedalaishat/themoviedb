package com.alaishat.ahmed.themoviedb.datasource.movie.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
@Serializable
data class ToggleWatchlistMovieReq(
    @SerialName("media_id") val mediaId: Int,
    @SerialName("watchlist") val watchlist: Boolean,
    @SerialName("media_type") val mediaType: String = "movie",
)