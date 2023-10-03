package com.alaishat.ahmed.themoviedb.presentation.feature.movie.model

/**
 * Created by Ahmed Al-Aishat on Sep/26/2023.
 * The Movie DB Project.
 */
sealed interface CreditsError {
    object Failed : CreditsError
    object Unknown : CreditsError
}