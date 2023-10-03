package com.alaishat.ahmed.themoviedb.domain.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
interface AccountRepository : Repository {
    fun getWatchListPagingFlow(): Flow<PagingData<MovieDomainModel>>
    suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean)
}