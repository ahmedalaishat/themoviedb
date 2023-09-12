package com.alaishat.ahmed.themoviedb.datasource.source.network

import com.alaishat.ahmed.themoviedb.data.model.MovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface MoviesDataSource {
    suspend fun getMoviesPage(movieListPath: String, page: Int): List<MovieDataModel>
    suspend fun searchMovie(query: String, page: Int): List<MovieDataModel>
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDataModel
    suspend fun getMovieCredits(movieId: Int): List<CreditDataModel>
    suspend fun getMovieReviews(movieId: Int, page: Int): List<ReviewDataModel>
    suspend fun addMovieRating(movieId: Int, rating: Int)
    suspend fun getMovieAccountStatus(movieId: Int): MovieAccountStatusDataModel
}