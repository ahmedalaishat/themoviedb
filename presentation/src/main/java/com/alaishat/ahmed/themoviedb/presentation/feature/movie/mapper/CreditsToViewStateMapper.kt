package com.alaishat.ahmed.themoviedb.presentation.feature.movie.mapper

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.CreditsViewState
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.toPresentation

/**
 * Created by Ahmed Al-Aishat on Sep/26/2023.
 * The Movie DB Project.
 */
fun CreditsDomainModel.toViewState() = when (this) {
    CreditsDomainModel.NoCache -> CreditsViewState.NoCache
    is CreditsDomainModel.Error -> CreditsViewState.Error(exception.toCreditsViewError())
    is CreditsDomainModel.Success -> CreditsViewState.Success(credits = credits.map { it.toPresentation() })
}