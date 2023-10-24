package com.alaishat.ahmed.themoviedb.presentation.feature.movie.model

/**
 * Created by Ahmed Al-Aishat on Sep/21/2023.
 * The Movie DB Project.
 */
sealed interface MovieDetailsError {
    object Failed : MovieDetailsError
    object Unknown : MovieDetailsError
}