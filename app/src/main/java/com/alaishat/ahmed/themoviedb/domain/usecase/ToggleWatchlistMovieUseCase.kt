package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class ToggleWatchlistMovieUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(movieId: Int, watchlist: Boolean) {
        moviesRepository.toggleCachedWatchlistMovie(movieId = movieId, watchlist = watchlist)
        try {
            accountRepository.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist)
        } catch (e: Exception) {
            // in case the request fails undo toggle cached movie
            moviesRepository.toggleCachedWatchlistMovie(movieId = movieId, watchlist = watchlist.not())
        }
    }
}