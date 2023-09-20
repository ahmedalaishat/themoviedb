package com.alaishat.ahmed.themoviedb.data.model

import com.alaishat.ahmed.themoviedb.domain.model.CreditDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
data class CreditDataModel(
    val creditId: String,
    val name: String,
    val profilePath: String?,
)

fun CreditDataModel.toDomain() = CreditDomainModel(
    id = creditId,
    name = name,
    profilePath = profilePath,
)

fun List<CreditDataModel>.mapToCreditsDomainModels() = map(CreditDataModel::toDomain)