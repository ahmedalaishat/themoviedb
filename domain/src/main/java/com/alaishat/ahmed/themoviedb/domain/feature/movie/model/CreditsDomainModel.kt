package com.alaishat.ahmed.themoviedb.domain.feature.movie.model

import com.alaishat.ahmed.themoviedb.domain.achitecture.exception.DomainException

/**
 * Created by Ahmed Al-Aishat on Sep/26/2023.
 * The Movie DB Project.
 */
sealed interface CreditsDomainModel {

    data class Success(
        val credits: List<CreditDomainModel>,
    ) : CreditsDomainModel

    data object NoCache : CreditsDomainModel

    data class Error(val exception: DomainException) : CreditsDomainModel
}