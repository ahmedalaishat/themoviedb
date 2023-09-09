package com.alaishat.ahmed.themoviedb.data.source.network

import com.alaishat.ahmed.themoviedb.data.model.NetworkCredit
import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.model.NetworkMovieDetails
import com.alaishat.ahmed.themoviedb.data.model.NetworkReview

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface NetworkMoviesDataSource {

    suspend fun getMoviesPage(movieListPath: String, page: Int): List<NetworkMovie>
    suspend fun searchMovie(query: String, page: Int): List<NetworkMovie>
    suspend fun getWatchList(page: Int): List<NetworkMovie>
    suspend fun getMovieDetails(movieId: Int): NetworkMovieDetails
    suspend fun getMovieCredits(movieId: Int): List<NetworkCredit>
    suspend fun getMovieReviews(movieId: Int, page: Int): List<NetworkReview>
}