package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
class GetMovieGenreListUseCase(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(): Flow<GenresDomainModel> = moviesRepository.getMovieGenreList()
}