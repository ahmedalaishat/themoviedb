package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
class GetMovieGenreListUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke() = moviesRepository.getMovieGenreList()
}