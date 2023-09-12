package com.alaishat.ahmed.themoviedb.datasource.impl.remote.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Serializable
data class ApiErrorBody(
    @SerialName("status_message") val message: String?,
)