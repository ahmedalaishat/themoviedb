package com.alaishat.ahmed.themoviedb.domain

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Review
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieReviewsUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository,
) {
    operator fun invoke(movieId: Int): Flow<PagingData<Review>> =
        movieListRepository.getMovieReviews(movieId = movieId)
}