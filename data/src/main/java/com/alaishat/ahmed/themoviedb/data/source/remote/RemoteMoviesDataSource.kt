package com.alaishat.ahmed.themoviedb.data.source.remote

import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface RemoteMoviesDataSource {
    suspend fun getMoviesPage(
        movieListTypeDataModel: MovieListTypeDataModel,
        page: Int,
    ): List<MovieDataModel>

    suspend fun fetchSearchMoviePage(query: String, page: Int): List<MovieDataModel>
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDataModel
    suspend fun getMovieCredits(movieId: Int): List<CreditDataModel>
    suspend fun getMovieReviewsPage(movieId: Int, page: Int): List<ReviewDataModel>
    suspend fun addMovieRating(movieId: Int, rating: Int)
    suspend fun getMovieAccountStatus(movieId: Int): MovieAccountStatusDataModel
    suspend fun getMovieGenreList(): List<GenreDataModel>
    suspend fun getWatchlistPage(page: Int): List<MovieDataModel>
    suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean)
}