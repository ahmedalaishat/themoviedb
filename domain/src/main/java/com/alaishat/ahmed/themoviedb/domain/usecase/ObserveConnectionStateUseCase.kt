package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.ConnectionRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
class ObserveConnectionStateUseCase(
    private val connectionRepository: ConnectionRepository,
) {
    operator fun invoke(): Flow<ConnectionStateDomainModel> = connectionRepository.observeConnectionState()
}