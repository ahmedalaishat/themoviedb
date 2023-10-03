package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieCreditsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(movieId: Int): Flow<CreditsDomainModel> =
    moviesRepository.getMovieCredits(movieId = movieId)
}