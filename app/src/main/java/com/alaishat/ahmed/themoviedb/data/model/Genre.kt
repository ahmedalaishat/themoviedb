package com.alaishat.ahmed.themoviedb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class Genre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)
