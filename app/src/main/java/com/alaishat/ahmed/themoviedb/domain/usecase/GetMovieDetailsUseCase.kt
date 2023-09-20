package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(movieId: Int): Flow<MovieDetailsDomainModel> = combine(
        moviesRepository.getMovieDetails(movieId = movieId),
        moviesRepository.observeWatchlist(movieId = movieId)
    ) { details, watchlistStatus ->
        if (details is MovieDetailsDomainModel.Success)
            details.copy(watchlist = watchlistStatus)
        else details
    }
}