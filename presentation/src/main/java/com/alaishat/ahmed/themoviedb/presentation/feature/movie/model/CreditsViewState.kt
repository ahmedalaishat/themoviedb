package com.alaishat.ahmed.themoviedb.presentation.feature.movie.model

/**
 * Created by Ahmed Al-Aishat on Sep/26/2023.
 * The Movie DB Project.
 */
sealed interface CreditsViewState {
    data class Success(
        val credits: List<Credit>
    ) : CreditsViewState

    data object Loading : CreditsViewState
    data object NoCache : CreditsViewState
    data class Error(val error: CreditsError) : CreditsViewState
}