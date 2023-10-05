package com.alaishat.ahmed.themoviedb.datasource.movie.model

import com.alaishat.ahmed.themoviedb.data.model.MovieAccountStatusDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkMovieAccountStatus(
    @SerialName("id") val id: Int,
    @SerialName("favorite") val favorite: Boolean,
//    @SerialName("rated") val rated: Boolean, // cased an issue because it could be false or an object contains the rate value
    @SerialName("watchlist") val watchlist: Boolean,
)

fun NetworkMovieAccountStatus.toMovieAccountStatusDataModel() =
    MovieAccountStatusDataModel(
        id = id,
        favorite = favorite,
        watchlist = watchlist,
    )