package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository


class GetWatchListUseCase(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(page: Int): List<MovieDomainModel> = moviesRepository.getWatchListPage(page)
}