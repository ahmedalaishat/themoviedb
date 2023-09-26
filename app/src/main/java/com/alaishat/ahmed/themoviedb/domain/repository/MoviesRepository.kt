package com.alaishat.ahmed.themoviedb.domain.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.CreditDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface MoviesRepository : Repository {

    fun getTopFiveMovies(): Flow<List<MovieDomainModel>>
    fun getMoviesPagingFlowByType(movieListTypeDomainModel: MovieListTypeDomainModel): Flow<PagingData<MovieDomainModel>>
    fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDomainModel>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsDomainModel>
    suspend fun cacheMovieWatchlistStatus(movieId: Int, watchlist: Boolean)
    fun observeWatchlist(movieId: Int): Flow<Boolean>
    fun getMovieReviewsPagingFlow(movieId: Int): Flow<PagingData<ReviewDomainModel>>
    suspend fun getMovieCredits(movieId: Int): List<CreditDomainModel>
    suspend fun addMovieRating(movieId: Int, rating: Int)
    fun getMovieGenreList(): Flow<List<GenreDomainModel>>
    suspend fun syncGenres(): Boolean
}