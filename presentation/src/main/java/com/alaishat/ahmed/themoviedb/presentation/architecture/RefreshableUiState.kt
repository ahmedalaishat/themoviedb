package com.alaishat.ahmed.themoviedb.presentation.architecture

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
data class RefreshableUiState<T>(
    val uiState: T,
    val isRefreshing: Boolean,
)