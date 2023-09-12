package com.alaishat.ahmed.themoviedb.architecture

import com.alaishat.ahmed.themoviedb.domain.model.MovieListType

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
enum class HomeTab(val tabName: String, val movieListType: MovieListType) {
    NOW_PLAYING("Now playing", MovieListType.NOW_PLAYING),
    POPULAR("Popular", MovieListType.POPULAR),
    TOP_RATED("Top rated", MovieListType.TOP_RATED),
    UPCOMING("Upcoming", MovieListType.UPCOMING),
}