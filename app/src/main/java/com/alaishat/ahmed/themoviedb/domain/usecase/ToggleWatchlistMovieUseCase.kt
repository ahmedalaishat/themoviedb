package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class ToggleWatchlistMovieUseCase(
    private val accountRepository: AccountRepository,
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(movieId: Int, watchlist: Boolean) {
        accountRepository.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist)
    }
}