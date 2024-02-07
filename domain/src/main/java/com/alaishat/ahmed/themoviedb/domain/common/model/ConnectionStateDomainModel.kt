package com.alaishat.ahmed.themoviedb.domain.common.model

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
sealed interface ConnectionStateDomainModel {
    data object Unset : ConnectionStateDomainModel
    data class Connected(
        val backOnline: Boolean,
    ) : ConnectionStateDomainModel

    data object Disconnected : ConnectionStateDomainModel
}