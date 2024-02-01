package com.alaishat.ahmed.themoviedb.presentation.common.model

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
sealed interface ConnectionStateUiModel {
    data object Offline : ConnectionStateUiModel
    data object BackOnline : ConnectionStateUiModel
    data object Online : ConnectionStateUiModel
}