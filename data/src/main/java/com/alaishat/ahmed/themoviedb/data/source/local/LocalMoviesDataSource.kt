package com.alaishat.ahmed.themoviedb.data.source.local

import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
interface LocalMoviesDataSource {

    fun cacheMovieList(
        movieListTypeDataModel: MovieListTypeDataModel,
        deleteCached: Boolean,
        movies: List<MovieDataModel>,
    )

    fun cacheMovies(movies: List<MovieDataModel>)

    fun getCachedMovieList(movieListTypeDataModel: MovieListTypeDataModel): List<MovieDataModel>
    fun getCachedWatchlistPage(page: Int): List<MovieDataModel>
    fun searchCachedMoviePage(query: String, page: Int): List<MovieDataModel>
    fun getCachedMoviesPagingFlow(
        movieListType: MovieListTypeDataModel,
        page: Int,
    ): List<MovieDataModel>

    fun cacheMovieReviews(movieId: Int, reviews: List<ReviewDataModel>)
    fun getCachedReviewsPage(movieId: Int, page: Int): List<ReviewDataModel>
    fun getMovieReviewsFlow(movieId: Int): List<ReviewDataModel>
    fun getMovieGenreList(): List<GenreDataModel>
    fun updateMovieGenreList(genreList: List<GenreDataModel>)
    fun cacheMovieCredits(movieId: Int, credits: List<CreditDataModel>)
    fun getCachedMovieCredits(movieId: Int): Flow<List<CreditDataModel>>
    fun cacheMovieDetails(movieDetailsDataModel: MovieDetailsDataModel)
    fun getCachedMovieDetails(movieId: Int): MovieDetailsDataModel?
    fun cacheMovieWatchlistStatus(movieId: Int, watchlist: Boolean)
    fun observeMovieWatchlistStatus(movieId: Int): Flow<Boolean>
}