package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.model.mapToCredits
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.model.toMoviesDetails
import com.alaishat.ahmed.themoviedb.data.pagingsource.MoviesPagingSource
import com.alaishat.ahmed.themoviedb.data.pagingsource.ReviewsPagingSource
import com.alaishat.ahmed.themoviedb.data.pagingsource.SearchPagingSource
import com.alaishat.ahmed.themoviedb.data.source.network.MoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.Credit
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetails
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.model.Review
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import io.ktor.client.utils.EmptyContent
import io.ktor.client.utils.EmptyContent.status
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
const val DEFAULT_PAGE_SIZE = 20

class MoviesRepositoryImpl @Inject constructor(
    private val moviesDataSource: MoviesDataSource,
) : MoviesRepository {
    private val watchlistSet = MutableStateFlow(setOf<Int>())

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

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails> = flow {
        coroutineScope {
            val status = async { moviesDataSource.getMovieAccountStatus(movieId = movieId) }
            val movieDetails = async { moviesDataSource.getMovieDetails(movieId = movieId) }
            toggleCachedWatchlistMovie(movieId = movieId, watchlist = status.await().watchlist)
            emit(movieDetails.await().toMoviesDetails())
        }
    }

    override suspend fun toggleCachedWatchlistMovie(movieId: Int, watchlist: Boolean) {
        if (watchlist)
            watchlistSet.update { it + movieId }
        else
            watchlistSet.update { it - movieId }
    }

    override fun observeWatchlist(): Flow<Set<Int>> {
        return watchlistSet
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                ReviewsPagingSource(
                    movieId = movieId,
                    moviesDataSource = moviesDataSource,
                )
            }
        ).flow
    }

    override suspend fun getMovieCredits(movieId: Int): List<Credit> {
        return moviesDataSource.getMovieCredits(movieId = movieId).mapToCredits()
    }

    override suspend fun addMovieRating(movieId: Int, rating: Int) {
        return moviesDataSource.addMovieRating(movieId = movieId, rating = rating)
    }
}