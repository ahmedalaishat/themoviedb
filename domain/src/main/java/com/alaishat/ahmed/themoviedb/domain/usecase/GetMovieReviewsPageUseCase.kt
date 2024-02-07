package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieReviewsPageUseCase(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(movieId: Int, page: Int): List<ReviewDomainModel> =
        moviesRepository.getMovieReviewsPage(movieId = movieId, page = page)
}