package com.alaishat.ahmed.themoviedb.domain

import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class AddMovieRatingUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(movieId: Int, rating: Int) =
        moviesRepository.addMovieRating(movieId = movieId, rating = rating)
}