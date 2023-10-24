package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class AddMovieRatingUseCase(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(movieId: Int, rating: Int): Boolean =
        moviesRepository.addMovieRating(movieId = movieId, rating = rating)
}