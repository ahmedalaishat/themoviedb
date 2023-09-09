package com.alaishat.ahmed.themoviedb.domain

import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetails
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieDetailsUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository,
) {
    operator fun invoke(movieId: Int): Flow<MovieDetails> = flow {
        val movie = movieListRepository.getMovieDetails(movieId = movieId)
        emit(movie)
    }
}