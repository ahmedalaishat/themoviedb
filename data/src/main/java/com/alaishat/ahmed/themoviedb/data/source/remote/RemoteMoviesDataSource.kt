package com.alaishat.ahmed.themoviedb.data.source.remote

import com.alaishat.ahmed.themoviedb.data.architecture.DataResult
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

    suspend fun fetchSearchMoviePage(query: String, page: Int): DataResult<List<MovieDataModel>>
    suspend fun getMovieDetails(movieId: Int): DataResult<MovieDetailsDataModel>
    suspend fun getMovieCredits(movieId: Int): DataResult<List<CreditDataModel>>
    suspend fun getMovieReviewsPage(movieId: Int, page: Int): DataResult<List<ReviewDataModel>>
    suspend fun addMovieRating(movieId: Int, rating: Int): DataResult<Unit>
    suspend fun getMovieAccountStatus(movieId: Int): DataResult<MovieAccountStatusDataModel>
    suspend fun getMovieGenreList(): DataResult<List<GenreDataModel>>
    suspend fun getWatchlistPage(page: Int): DataResult<List<MovieDataModel>>
    suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean): DataResult<Unit>
}