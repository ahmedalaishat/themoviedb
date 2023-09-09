package com.alaishat.ahmed.themoviedb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class AuthorDetails(
    @SerialName("username") val username: String,
    @SerialName("avatar_path") val avatarPath: String?,
    @SerialName("rating") val rating: Float,
)