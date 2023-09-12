package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.model.mapToCreditsDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToGenresDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.model.toMoviesDetailsDomainModel
import com.alaishat.ahmed.themoviedb.data.pagingsource.MoviesPagingSource
import com.alaishat.ahmed.themoviedb.data.pagingsource.ReviewsPagingSource
import com.alaishat.ahmed.themoviedb.data.pagingsource.SearchPagingSource
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.network.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.CreditDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieListType
import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
const val DEFAULT_PAGE_SIZE = 20

class MoviesRepositoryImpl @Inject constructor(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
) : MoviesRepository {
    private val watchlistSet = MutableStateFlow(setOf<Int>())

    private fun getMovieListPathByType(movieListType: MovieListType) =
        when (movieListType) {
            MovieListType.NOW_PLAYING -> "now_playing"
            MovieListType.POPULAR -> "popular"
            MovieListType.TOP_RATED -> "top_rated"
            MovieListType.UPCOMING -> "upcoming"
        }

    override suspend fun getMoviesPageByType(movieListType: MovieListType, page: Int): List<MovieDomainModel> {
        val movieListPath = getMovieListPathByType(movieListType)
        return remoteMoviesDataSource.getMoviesPage(movieListPath, page).mapToMovies()
    }

    override fun getMoviesPagingFlowByType(movieListType: MovieListType): Flow<PagingData<MovieDomainModel>> {
        val movieListPath = getMovieListPathByType(movieListType)
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviesPagingSource(
                    movieListPath = movieListPath,
                    remoteMoviesDataSource = remoteMoviesDataSource,
                )
            }
        ).flow
    }

    override fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                SearchPagingSource(
                    query = query,
                    remoteMoviesDataSource = remoteMoviesDataSource,
                )
            }
        ).flow
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsDomainModel> = flow {
        coroutineScope {
            val status = async { remoteMoviesDataSource.getMovieAccountStatus(movieId = movieId) }
            val movieDetails = async { remoteMoviesDataSource.getMovieDetails(movieId = movieId) }
            toggleCachedWatchlistMovie(movieId = movieId, watchlist = status.await().watchlist)
            emit(movieDetails.await().toMoviesDetailsDomainModel())
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

    override fun getMovieReviews(movieId: Int): Flow<PagingData<ReviewDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                ReviewsPagingSource(
                    movieId = movieId,
                    remoteMoviesDataSource = remoteMoviesDataSource,
                )
            }
        ).flow
    }

    override suspend fun getMovieCredits(movieId: Int): List<CreditDomainModel> {
        return remoteMoviesDataSource.getMovieCredits(movieId = movieId).mapToCreditsDomainModels()
    }

    override suspend fun addMovieRating(movieId: Int, rating: Int) {
        return remoteMoviesDataSource.addMovieRating(movieId = movieId, rating = rating)
    }

    override fun getMovieGenreList(): Flow<List<GenreDomainModel>> {
        return localMoviesDataSource.getMovieGenreList().map { it.mapToGenresDomainModels() }
    }

    //AHMED_TODO: refactor me
    override suspend fun syncGenres(): Boolean {
        val fetchedGenreList = remoteMoviesDataSource.getMovieGenreList()
        localMoviesDataSource.updateMovieGenreList(fetchedGenreList)
        Timber.e("Syncedd")
        return true
    }
}