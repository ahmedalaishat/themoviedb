package com.alaishat.ahmed.themoviedb.datasource.movie.model

import com.alaishat.ahmed.themoviedb.data.model.AuthorDetailsDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkAuthorDetailsModel(
    @SerialName("username") val username: String,
    @SerialName("avatar_path") val avatarPath: String?,
    @SerialName("rating") val rating: Float?,
)

fun NetworkAuthorDetailsModel.toAuthorDetailsDataModel() =
    AuthorDetailsDataModel(
        username = username,
        avatarPath = avatarPath,
        rating = rating,
    )