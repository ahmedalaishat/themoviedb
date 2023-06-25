package com.alaishat.ahmed.themoviedb.data.source.network

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface NetworkMovieListsDataSource {

    suspend fun getList(movieListPath: String): List<NetworkMovie>
    suspend fun searchMovie(query: String): List<NetworkMovie>
}