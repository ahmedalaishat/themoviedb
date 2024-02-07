package com.alaishat.ahmed.themoviedb.data.mapper

import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel

/**
 * Created by Ahmed Al-Aishat on Feb/08/2024.
 * The Movie DB Project.
 */
fun MovieListTypeDomainModel.toDataModel(): MovieListTypeDataModel {
    return when (this) {
        MovieListTypeDomainModel.NOW_PLAYING -> MovieListTypeDataModel.NOW_PLAYING
        MovieListTypeDomainModel.POPULAR -> MovieListTypeDataModel.POPULAR
        MovieListTypeDomainModel.TOP_RATED -> MovieListTypeDataModel.TOP_RATED
        MovieListTypeDomainModel.UPCOMING -> MovieListTypeDataModel.UPCOMING
    }
}