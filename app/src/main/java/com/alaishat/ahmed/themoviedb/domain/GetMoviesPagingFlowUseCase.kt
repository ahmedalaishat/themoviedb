package com.alaishat.ahmed.themoviedb.domain

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class GetMoviesPagingFlowUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository,
) {
    operator fun invoke(movieListType: MovieListType): Flow<PagingData<Movie>> =
        movieListRepository.getMoviesPagingFlowByType(movieListType)
}