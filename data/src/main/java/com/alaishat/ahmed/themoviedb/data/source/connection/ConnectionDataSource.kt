package com.alaishat.ahmed.themoviedb.data.source.connection

import com.alaishat.ahmed.themoviedb.data.model.ConnectionStateDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
interface ConnectionDataSource {
    fun observeIsConnected(): Flow<ConnectionStateDataModel>
    fun getConnectionState(): ConnectionStateDataModel
}