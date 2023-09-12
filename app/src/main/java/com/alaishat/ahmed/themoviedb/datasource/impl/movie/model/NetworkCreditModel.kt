package com.alaishat.ahmed.themoviedb.datasource.impl.movie.model

import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.domain.model.CreditDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Serializable
data class NetworkCreditModel(
    @SerialName("credit_id") val creditId: String,
    @SerialName("name") val name: String,
    @SerialName("profile_path") val profilePath: String?,
)

fun NetworkCreditModel.toCreditDataModel() = CreditDataModel(
    creditId = creditId,
    name = name,
    profilePath = profilePath,
)

fun List<NetworkCreditModel>.mapToCreditsDataModels() = map(NetworkCreditModel::toCreditDataModel)