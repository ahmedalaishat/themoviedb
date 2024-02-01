package com.alaishat.ahmed.themoviedb.domain.repository

import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface MoviesRepository : Repository {

    fun getTopFiveMovies(): Flow<List<MovieDomainModel>>
    suspend fun getMoviesPageByType(
        movieListTypeDomainModel: MovieListTypeDomainModel,
        page: Int,
    ): List<MovieDomainModel>

    suspend fun getSearchMoviePage(query: String, page: Int): List<MovieDomainModel>
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsDomainModel>
    fun isWatchlist(movieId: Int): Flow<Boolean>
    suspend fun getMovieReviewsPage(movieId: Int, page: Int): List<ReviewDomainModel>
    fun getMovieCredits(movieId: Int): Flow<CreditsDomainModel>
    suspend fun addMovieRating(movieId: Int, rating: Int): Boolean
    fun getMovieGenreList(): Flow<GenresDomainModel>
    suspend fun getWatchListPage(page: Int): List<MovieDomainModel>
    suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean)
}