package com.alaishat.ahmed.themoviedb.domain

import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/26/2023.
 * The Movie DB Project.
 */
class GetWatchListUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository,
) {
    operator fun invoke(): Flow<List<Movie>> = movieListRepository.getWatchList()
}