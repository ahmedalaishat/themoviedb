package com.alaishat.ahmed.themoviedb.data.source.network

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
interface AccountDataSource {
    suspend fun getWatchList(page: Int): List<NetworkMovie>
    suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean)
}