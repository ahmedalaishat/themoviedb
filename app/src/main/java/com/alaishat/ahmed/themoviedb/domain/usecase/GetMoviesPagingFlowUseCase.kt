package com.alaishat.ahmed.themoviedb.domain.usecase

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class GetMoviesPagingFlowUseCase(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(movieListTypeDomainModel: MovieListTypeDomainModel): Flow<PagingData<MovieDomainModel>> =
        moviesRepository.getMoviesPagingFlowByType(movieListTypeDomainModel)
}