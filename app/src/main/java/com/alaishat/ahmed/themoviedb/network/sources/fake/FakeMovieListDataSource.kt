package com.alaishat.ahmed.themoviedb.network.sources.fake

import com.alaishat.ahmed.themoviedb.TestRes
import com.alaishat.ahmed.themoviedb.network.sources.NetworkMovieListsDataSource

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class FakeMovieListDataSource : NetworkMovieListsDataSource {
    override suspend fun getList(movieList: NetworkMovieListsDataSource.MovieList): TestRes {
        TODO("Not yet implemented")
    }
}