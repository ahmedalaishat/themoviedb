package com.alaishat.ahmed.themoviedb.data.source.network

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface NetworkMoviesDataSource {

    suspend fun getMoviesPage(movieListPath: String, page: Int): List<NetworkMovie>
    suspend fun searchMovie(query: String,page: Int): List<NetworkMovie>
    suspend fun getWatchList(page: Int): List<NetworkMovie>
}