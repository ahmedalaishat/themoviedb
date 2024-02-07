package com.alaishat.ahmed.themoviedb.data.model

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
sealed interface ConnectionStateDataModel {
    data object Unset : ConnectionStateDataModel
    data class Connected(
        val backOnline: Boolean,
    ) : ConnectionStateDataModel

    data object Disconnected : ConnectionStateDataModel
}