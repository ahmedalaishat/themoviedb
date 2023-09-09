package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.Credit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkCredit(
    @SerialName("credit_id") val creditId: String,
    @SerialName("name") val name: String,
    @SerialName("profile_path") val profilePath: String?,
)

fun NetworkCredit.toCredit() = Credit(
    id = creditId,
    name = name,
    profilePath = profilePath,
)

fun List<NetworkCredit>.mapToCredits() = map(NetworkCredit::toCredit)