package com.alaishat.ahmed.themoviedb.network.datasource.fake

import com.alaishat.ahmed.themoviedb.data.model.NetworkCredit
import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.model.NetworkMovieDetails
import com.alaishat.ahmed.themoviedb.data.model.NetworkReview
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMoviesDataSource

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class FakeMoviesDataSource : NetworkMoviesDataSource {
    override suspend fun getMoviesPage(movieListPath: String, page: Int): List<NetworkMovie> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(query: String, page: Int): List<NetworkMovie> {
        TODO("Not yet implemented")
    }

    override suspend fun getWatchList(page: Int): List<NetworkMovie> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(movieId: Int): NetworkMovieDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieCredits(movieId: Int): List<NetworkCredit> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): List<NetworkReview> {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieRating(movieId: Int, rating: Int) {
        TODO("Not yet implemented")
    }
}