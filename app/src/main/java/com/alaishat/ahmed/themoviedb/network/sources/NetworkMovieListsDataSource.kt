package com.alaishat.ahmed.themoviedb.network.sources

import com.alaishat.ahmed.themoviedb.TestRes

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface NetworkMovieListsDataSource {

    suspend fun getList(movieList: MovieList): TestRes

    enum class MovieList(val listName: String) {
        NOW_PLAYING("now_playing"),
        POPULAR("popular"),
        TOP_RATED("top_rated"),
        UPCOMING("upcoming"),
    }
}