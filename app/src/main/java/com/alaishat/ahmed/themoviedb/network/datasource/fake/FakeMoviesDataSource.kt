package com.alaishat.ahmed.themoviedb.network.datasource.fake

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMoviesDataSource

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class FakeMoviesDataSource : NetworkMoviesDataSource {
    override suspend fun getList(movieListPath: String): List<NetworkMovie> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(query: String): List<NetworkMovie> {
        TODO("Not yet implemented")
    }

    override suspend fun getWatchList(): List<NetworkMovie> {
        TODO("Not yet implemented")
    }
}