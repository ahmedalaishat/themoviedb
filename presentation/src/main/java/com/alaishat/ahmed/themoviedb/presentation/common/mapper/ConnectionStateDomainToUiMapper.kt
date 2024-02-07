package com.alaishat.ahmed.themoviedb.presentation.common.mapper

import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel
import com.alaishat.ahmed.themoviedb.presentation.common.model.ConnectionStateUiModel

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
fun ConnectionStateDomainModel.toUiModel() = when {
    this is ConnectionStateDomainModel.Connected && this.backOnline -> ConnectionStateUiModel.BackOnline

    this == ConnectionStateDomainModel.Disconnected -> ConnectionStateUiModel.Offline
    else -> ConnectionStateUiModel.Online
}