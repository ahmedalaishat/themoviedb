package com.alaishat.ahmed.themoviedb.domain.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
interface MovieListRepository : Repository {

    suspend fun getMoviesPageByType(movieListType: MovieListType, page: Int): List<Movie>
    fun getMoviesPagingFlowByType(movieListType: MovieListType): Flow<PagingData<Movie>>
    fun getSearchMoviePagingFlow(query: String): Flow<PagingData<Movie>>
    fun getWatchListPagingFlow(): Flow<PagingData<Movie>>
}