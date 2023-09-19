package com.alaishat.ahmed.themoviedb.datasource.source.network

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.feature.movie.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface RemoteMoviesDataSource {
    suspend fun getMoviesPage(movieListTypeDataModel: MovieListTypeDataModel, page: Int): List<MovieDataModel>
    fun getCacheableMoviesPagingFlow(
        movieListTypeDataModel: MovieListTypeDataModel,
        pageCachingHandler: suspend (page: Int, pageData: List<MovieDataModel>) -> Unit,
    ): Flow<PagingData<MovieDataModel>>

    suspend fun searchMovie(query: String, page: Int): List<MovieDataModel>
    fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDataModel>>
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDataModel
    suspend fun getMovieCredits(movieId: Int): List<CreditDataModel>
    suspend fun getMovieReviews(movieId: Int, page: Int): List<ReviewDataModel>
    fun getMovieReviewsPagingFlow(
        movieId: Int,
        pageCachingHandler: suspend (page: Int, pageData: List<ReviewDataModel>) -> Unit
    ): Flow<PagingData<ReviewDataModel>>

    suspend fun addMovieRating(movieId: Int, rating: Int)
    suspend fun getMovieAccountStatus(movieId: Int): MovieAccountStatusDataModel
    suspend fun getMovieGenreList(): List<GenreDataModel>
}