package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
//AHMED_TODO: refactor me
class SyncUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke() = moviesRepository.syncGenres()
}