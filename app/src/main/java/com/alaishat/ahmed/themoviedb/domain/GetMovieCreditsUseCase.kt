package com.alaishat.ahmed.themoviedb.domain

import com.alaishat.ahmed.themoviedb.domain.model.Credit
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieCreditsUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository,
) {
    operator fun invoke(movieId: Int): Flow<List<Credit>> = flow {
        val credits = movieListRepository.getMovieCredits(movieId = movieId)
        emit(credits)
    }
}