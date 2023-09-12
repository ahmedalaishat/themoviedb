package com.alaishat.ahmed.themoviedb.datasource.source.network

import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
interface RemoteAccountDataSource {
    suspend fun getWatchList(page: Int): List<MovieDataModel>
    suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean)
}