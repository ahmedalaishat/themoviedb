package com.alaishat.ahmed.themoviedb.data.repository.fake

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class FakeAccountRepository:AccountRepository {
    override fun getWatchListPagingFlow(): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) {
        TODO("Not yet implemented")
    }
}