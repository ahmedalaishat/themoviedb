package com.alaishat.ahmed.themoviedb.presentation.common.model

/**
 * Created by Ahmed Al-Aishat on Oct/04/2023.
 * The Movie DB Project.
 */

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    object NoCache : MainActivityUiState
    data class Success(val genres: List<Genre>) : MainActivityUiState
}