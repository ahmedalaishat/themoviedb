package com.alaishat.ahmed.themoviedb.datasource.impl.movie.model

import com.alaishat.ahmed.themoviedb.domain.model.MovieListTypeDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
enum class MovieListTypeDataModel(val listApiPath:String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming"),
    TOP_FIVE("top_rated"),
    WATCHLIST("");
    companion object{
         fun getByMovieListTypeDomainModel(movieListTypeDomainModel: MovieListTypeDomainModel): MovieListTypeDataModel {
             return when(movieListTypeDomainModel){
                 MovieListTypeDomainModel.NOW_PLAYING -> NOW_PLAYING
                 MovieListTypeDomainModel.POPULAR -> POPULAR
                 MovieListTypeDomainModel.TOP_RATED -> TOP_RATED
                 MovieListTypeDomainModel.UPCOMING -> UPCOMING
             }
         }
    }
}