package com.alaishat.ahmed.themoviedb.data.model

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
enum class MovieListTypeDataModel(val listApiPath: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming"),
    TOP_FIVE("top_rated"),
    WATCHLIST("");
}