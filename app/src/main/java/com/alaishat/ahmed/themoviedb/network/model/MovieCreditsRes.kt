package com.alaishat.ahmed.themoviedb.network.model

import com.alaishat.ahmed.themoviedb.data.model.NetworkCredit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class MovieCreditsRes(
    @SerialName("cast") val cast: List<NetworkCredit>
)