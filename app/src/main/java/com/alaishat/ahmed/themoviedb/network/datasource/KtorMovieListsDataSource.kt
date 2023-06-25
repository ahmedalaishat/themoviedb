package com.alaishat.ahmed.themoviedb.network.datasource

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMovieListsDataSource
import com.alaishat.ahmed.themoviedb.network.KtorClient
import com.alaishat.ahmed.themoviedb.network.model.MovieListRes
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

    override suspend fun getList(movieListPath: String): List<NetworkMovie> {
        //AHMED_TODO: convert me to pager
        val res: MovieListRes = ktorClient.call {
            get("movie/$movieListPath")
        }
        return res.results
    }

    override suspend fun searchMovie(query: String): List<NetworkMovie> {
        //AHMED_TODO: convert me to pager
        val res: MovieListRes = ktorClient.call {
            get("search/movie?query=$query")
        }
        return res.results
    }
}