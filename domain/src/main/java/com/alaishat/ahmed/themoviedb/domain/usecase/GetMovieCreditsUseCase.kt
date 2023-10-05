package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieCreditsUseCase(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(movieId: Int): Flow<CreditsDomainModel> =
        moviesRepository.getMovieCredits(movieId = movieId)
}