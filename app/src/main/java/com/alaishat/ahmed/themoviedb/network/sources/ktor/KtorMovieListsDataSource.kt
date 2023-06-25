package com.alaishat.ahmed.themoviedb.network.sources.ktor

import com.alaishat.ahmed.themoviedb.TestRes
import com.alaishat.ahmed.themoviedb.network.KtorClient
import com.alaishat.ahmed.themoviedb.network.sources.NetworkMovieListsDataSource
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Singleton
class KtorMovieListsDataSource @Inject constructor(
    private val ktorClient: KtorClient,
) : NetworkMovieListsDataSource {

    override suspend fun getList(movieList: NetworkMovieListsDataSource.MovieList): TestRes = ktorClient.call {
        get("movie/${movieList.listName}")
    }
}