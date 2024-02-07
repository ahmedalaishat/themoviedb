package com.alaishat.ahmed.themoviedb.data.mapper

import com.alaishat.ahmed.themoviedb.data.model.ConnectionStateDataModel
import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
fun ConnectionStateDataModel.toDomain() = when (this) {
    is ConnectionStateDataModel.Connected -> ConnectionStateDomainModel.Connected(backOnline)
    ConnectionStateDataModel.Disconnected -> ConnectionStateDomainModel.Disconnected
    ConnectionStateDataModel.Unset -> ConnectionStateDomainModel.Unset
}