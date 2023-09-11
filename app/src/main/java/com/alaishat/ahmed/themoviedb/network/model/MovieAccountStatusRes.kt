package com.alaishat.ahmed.themoviedb.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
@Serializable
data class MovieAccountStatusRes(
    @SerialName("id") val id: Int,
    @SerialName("favorite") val favorite: Boolean,
//    @SerialName("rated") val rated: Boolean, // cased an issue because it could be false or an object contains the rate value
    @SerialName("watchlist") val watchlist: Boolean,
)