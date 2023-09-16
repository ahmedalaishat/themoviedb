package com.alaishat.ahmed.themoviedb.domain.usecase

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieReviewsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(movieId: Int): Flow<PagingData<ReviewDomainModel>> =
        moviesRepository.getMovieReviewsPagingFlow(movieId = movieId)
}