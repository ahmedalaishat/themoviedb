package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class GetMoviesPageUseCase(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(
        movieListTypeDomainModel: MovieListTypeDomainModel,
        page: Int,
    ): List<MovieDomainModel> =
        moviesRepository.getMoviesPageByType(movieListTypeDomainModel, page)
}