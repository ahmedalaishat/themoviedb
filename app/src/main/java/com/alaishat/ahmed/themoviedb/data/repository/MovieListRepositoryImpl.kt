package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.pagingsource.MoviesPagingSource
import com.alaishat.ahmed.themoviedb.data.pagingsource.SearchPagingSource
import com.alaishat.ahmed.themoviedb.data.pagingsource.WatchListPagingSource
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
private const val DEFAULT_PAGE_SIZE = 20

class MovieListRepositoryImpl @Inject constructor(
    private val moviesDataSource: NetworkMoviesDataSource,
) : MovieListRepository {

    private fun getMovieListPathByType(movieListType: MovieListType) =
        when (movieListType) {
            MovieListType.NOW_PLAYING -> "now_playing"
            MovieListType.POPULAR -> "popular"
            MovieListType.TOP_RATED -> "top_rated"
            MovieListType.UPCOMING -> "upcoming"
        }

    override suspend fun getMoviesPageByType(movieListType: MovieListType, page: Int): List<Movie> {
        val movieListPath = getMovieListPathByType(movieListType)
        return moviesDataSource.getMoviesPage(movieListPath, page).mapToMovies()
    }

    override fun getMoviesPagingFlowByType(movieListType: MovieListType): Flow<PagingData<Movie>> {
        val movieListPath = getMovieListPathByType(movieListType)
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviesPagingSource(
                    movieListPath = movieListPath,
                    moviesDataSource = moviesDataSource,
                )
            }
        ).flow
    }

    override fun getSearchMoviePagingFlow(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                SearchPagingSource(
                    query = query,
                    moviesDataSource = moviesDataSource,
                )
            }
        ).flow
    }

    override fun getWatchListPagingFlow(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                WatchListPagingSource(
                    moviesDataSource = moviesDataSource,
                )
            }
        ).flow
    }
}