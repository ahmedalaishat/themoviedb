package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository

class SearchMovieUseCase(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(query: String, page: Int): List<MovieDomainModel> {
        return moviesRepository.getSearchMoviePage(query, page)
    }
}