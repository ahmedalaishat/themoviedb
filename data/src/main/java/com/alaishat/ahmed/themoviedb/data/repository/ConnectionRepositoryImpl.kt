package com.alaishat.ahmed.themoviedb.data.repository

import com.alaishat.ahmed.themoviedb.data.mapper.toDomain
import com.alaishat.ahmed.themoviedb.data.source.connection.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.domain.repository.BackgroundExecutor
import com.alaishat.ahmed.themoviedb.domain.repository.ConnectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
class ConnectionRepositoryImpl(
    override val ioDispatcher: CoroutineDispatcher,
    private val connectionDataSource: ConnectionDataSource,
) : ConnectionRepository, BackgroundExecutor {
    override fun observeConnectionState() =
        connectionDataSource.observeIsConnected().map { it.toDomain() }

    override fun getConnectionState() = connectionDataSource.getConnectionState().toDomain()

}