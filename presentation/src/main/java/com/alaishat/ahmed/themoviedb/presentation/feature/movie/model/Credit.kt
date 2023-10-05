package com.alaishat.ahmed.themoviedb.presentation.feature.movie.model

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/26/2023.
 * The Movie DB Project.
 */
data class Credit(
    val id: String,
    val name: String,
    val profilePath: String?,
)

fun CreditDomainModel.toPresentation() = Credit(
    id = id,
    name = name,
    profilePath = profilePath,
)