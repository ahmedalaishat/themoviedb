package com.alaishat.ahmed.themoviedb.domain.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.CreditDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface MoviesRepository : Repository {

    suspend fun getMoviesPageByType(movieListType: MovieListType, page: Int): List<MovieDomainModel>
    fun getMoviesPagingFlowByType(movieListType: MovieListType): Flow<PagingData<MovieDomainModel>>
    fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDomainModel>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsDomainModel>
    suspend fun toggleCachedWatchlistMovie(movieId: Int, watchlist: Boolean)
    fun observeWatchlist(): Flow<Set<Int>>
    fun getMovieReviews(movieId: Int): Flow<PagingData<ReviewDomainModel>>
    suspend fun getMovieCredits(movieId: Int): List<CreditDomainModel>
    suspend fun addMovieRating(movieId: Int, rating: Int)
}