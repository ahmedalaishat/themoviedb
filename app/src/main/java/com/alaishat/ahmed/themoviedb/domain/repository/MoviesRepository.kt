package com.alaishat.ahmed.themoviedb.domain.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.GenresDomainModel
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
    fun observeWatchlist(movieId: Int): Flow<Boolean>
    fun getMovieReviewsPagingFlow(movieId: Int): Flow<PagingData<ReviewDomainModel>>
    fun getMovieCredits(movieId: Int): Flow<CreditsDomainModel>
    suspend fun addMovieRating(movieId: Int, rating: Int):Boolean
    fun getMovieGenreList(): Flow<GenresDomainModel>
}