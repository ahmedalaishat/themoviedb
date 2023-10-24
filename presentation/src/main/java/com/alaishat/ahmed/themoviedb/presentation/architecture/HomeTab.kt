package com.alaishat.ahmed.themoviedb.presentation.architecture

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
enum class HomeTab(val tabName: String, val movieListTypeDomainModel: MovieListTypeDomainModel) {
    NOW_PLAYING("Now playing", MovieListTypeDomainModel.NOW_PLAYING),
    POPULAR("Popular", MovieListTypeDomainModel.POPULAR),
    TOP_RATED("Top rated", MovieListTypeDomainModel.TOP_RATED),
    UPCOMING("Upcoming", MovieListTypeDomainModel.UPCOMING),
}