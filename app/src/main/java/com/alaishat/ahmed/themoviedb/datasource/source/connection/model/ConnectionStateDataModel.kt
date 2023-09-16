package com.alaishat.ahmed.themoviedb.datasource.source.connection.model

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
sealed interface ConnectionStateDataModel {
    object Unset : ConnectionStateDataModel
    object Connected : ConnectionStateDataModel
    object Disconnected : ConnectionStateDataModel
}