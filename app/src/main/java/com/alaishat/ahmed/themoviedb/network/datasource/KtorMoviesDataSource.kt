package com.alaishat.ahmed.themoviedb.network.datasource

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMoviesDataSource
import com.alaishat.ahmed.themoviedb.network.KtorClient
import com.alaishat.ahmed.themoviedb.network.model.MovieListRes
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Singleton
class KtorMoviesDataSource @Inject constructor(
    private val ktorClient: KtorClient,
) : NetworkMoviesDataSource {

    override suspend fun getMoviesPage(movieListPath: String, page: Int): List<NetworkMovie> {
        val res: MovieListRes = ktorClient.call {
            get("movie/$movieListPath?page=$page&without_keywords=158718")
        }
        delay(2000)
        return res.results
    }

    override suspend fun searchMovie(query: String, page: Int): List<NetworkMovie> {
        val res: MovieListRes = ktorClient.call {
            get("search/movie?query=$query&page=$page")
        }
        return res.results
    }

    override suspend fun getWatchList(page: Int): List<NetworkMovie> {
        val res: MovieListRes = ktorClient.call {
            get("account/9712119/watchlist/movies?page=$page")
        }
        return res.results
    }
}