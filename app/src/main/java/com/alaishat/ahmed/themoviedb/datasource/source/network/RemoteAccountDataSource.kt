package com.alaishat.ahmed.themoviedb.datasource.source.network

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
interface RemoteAccountDataSource {
    suspend fun getWatchlist(page: Int): List<MovieDataModel>
    fun getWatchlistPagingFlow(
        pageCachingHandler: suspend (page: Int, pageData: List<MovieDataModel>) -> Unit,
    ): Flow<PagingData<MovieDataModel>>

    suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean)
}