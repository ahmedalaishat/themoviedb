package com.alaishat.ahmed.themoviedb.domain.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Credit
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetails
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.model.Review
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface MoviesRepository : Repository {

    suspend fun getMoviesPageByType(movieListType: MovieListType, page: Int): List<Movie>
    fun getMoviesPagingFlowByType(movieListType: MovieListType): Flow<PagingData<Movie>>
    fun getSearchMoviePagingFlow(query: String): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetails>
    suspend fun toggleCachedWatchlistMovie(movieId: Int, watchlist: Boolean)
    fun observeWatchlist(): Flow<Set<Int>>
    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
    suspend fun getMovieCredits(movieId: Int): List<Credit>
    suspend fun addMovieRating(movieId: Int, rating: Int)
}