package com.alaishat.ahmed.themoviedb.domain.repository

import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
interface ConnectionRepository {
    fun observeConnectionState(): Flow<ConnectionStateDomainModel>
    fun getConnectionState(): ConnectionStateDomainModel
}
