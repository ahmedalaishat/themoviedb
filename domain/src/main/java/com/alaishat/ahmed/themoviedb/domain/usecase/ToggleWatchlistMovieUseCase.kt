package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class ToggleWatchlistMovieUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(movieId: Int, watchlist: Boolean) {
        accountRepository.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist)
    }
}