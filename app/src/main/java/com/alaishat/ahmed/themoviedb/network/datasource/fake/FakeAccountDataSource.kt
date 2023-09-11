package com.alaishat.ahmed.themoviedb.network.datasource.fake

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.source.network.AccountDataSource

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class FakeAccountDataSource : AccountDataSource {
    override suspend fun getWatchList(page: Int): List<NetworkMovie> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) {
        TODO("Not yet implemented")
    }
}