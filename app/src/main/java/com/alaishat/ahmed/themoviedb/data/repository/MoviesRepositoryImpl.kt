package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import com.alaishat.ahmed.themoviedb.data.mapper.toMovieDomainException
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.data.model.mapToCreditsDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToGenresDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.model.toMovieDomainModel
import com.alaishat.ahmed.themoviedb.data.model.toReviewDomainModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.network.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.di.AppDispatchers
import com.alaishat.ahmed.themoviedb.di.Dispatcher
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.CreditDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.BackgroundExecutor
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class MoviesRepositoryImpl @Inject constructor(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val connectionDataSource: ConnectionDataSource,
    private val movieDetailsToDomainResolver: MovieDetailsToDomainResolver,
    @Dispatcher(AppDispatchers.IO) override val ioDispatcher: CoroutineDispatcher,
) : MoviesRepository, BackgroundExecutor {
    private val watchlistSet = MutableStateFlow(setOf<Int>())

    override suspend fun getTopFiveMovies(): List<MovieDomainModel> = doInBackground {
        val movies = remoteMoviesDataSource.getMoviesPage(
            movieListTypeDataModel = MovieListTypeDataModel.TOP_FIVE,
            page = 1,
        ).take(5)
        localMoviesDataSource.cacheMovieList(
            deleteCached = true,
            movieListTypeDataModel = MovieListTypeDataModel.TOP_FIVE,
            movies = movies,
        )
        movies.mapToMovies()
    }

    override fun getMoviesPagingFlowByType(movieListTypeDomainModel: MovieListTypeDomainModel): Flow<PagingData<MovieDomainModel>> =
        remoteMoviesDataSource.getCacheableMoviesPagingFlow(
            movieListTypeDataModel = MovieListTypeDataModel.getByMovieListTypeDomainModel(movieListTypeDomainModel),
            pageCachingHandler = { page, pageData ->
                localMoviesDataSource.cacheMovieList(
                    deleteCached = page == 1,
                    movieListTypeDataModel = MovieListTypeDataModel.getByMovieListTypeDomainModel(
                        movieListTypeDomainModel
                    ),
                    movies = pageData,
                )
            }
        )
            .mapData(MovieDataModel::toMovieDomainModel)
            .flowOnBackground()

    override fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDomainModel>> =
        remoteMoviesDataSource.getSearchMoviePagingFlow(query = query)
            .mapData(MovieDataModel::toMovieDomainModel)
            .flowOnBackground()

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsDomainModel> {
        return connectionDataSource.observeIsConnected()
            .map { connected ->
                movieDetailsToDomainResolver.toDomain(
                    connectionState = connected,
                    remoteMovieProvider = {
                        coroutineScope {
                            val status = async { remoteMoviesDataSource.getMovieAccountStatus(movieId = movieId) }
                            val movieDetails = async { remoteMoviesDataSource.getMovieDetails(movieId = movieId) }
                            localMoviesDataSource.cacheMovieDetails(movieDetails.await())
                            localMoviesDataSource.cacheMovieWatchlistStatus(
                                movieId = movieId,
                                watchlist = status.await().watchlist
                            )
                            movieDetails.await()
                        }
                    },
                    localMovieProvider = {
                        delay(200)
                        localMoviesDataSource.getCachedMovieDetails(movieId = movieId)
                    }
                )
            }.retryWhen { cause, _ ->
                emit(MovieDetailsDomainModel.Error(cause.toMovieDomainException()))
                delay(1000)
                true
            }
            .flowOnBackground()
    }

    override suspend fun cacheMovieWatchlistStatus(movieId: Int, watchlist: Boolean) {
        if (watchlist)
            watchlistSet.update { it + movieId }
        else
            watchlistSet.update { it - movieId }
    }

    override fun observeWatchlist(movieId: Int): Flow<Boolean> {
        return localMoviesDataSource.observeMovieWatchlistStatus(movieId = movieId)
    }

    override fun getMovieReviewsPagingFlow(movieId: Int): Flow<PagingData<ReviewDomainModel>> =
        remoteMoviesDataSource.getMovieReviewsPagingFlow(
            movieId = movieId,
            pageCachingHandler = { _, reviews ->
                localMoviesDataSource.cacheMovieReviews(movieId = movieId, reviews = reviews)
            }
        )
            .mapData(ReviewDataModel::toReviewDomainModel)
            .flowOnBackground()

    override suspend fun getMovieCredits(movieId: Int): List<CreditDomainModel> = doInBackground {
        try {
            val credits = remoteMoviesDataSource.getMovieCredits(movieId = movieId)
            localMoviesDataSource.cacheMovieCredits(movieId, credits)
            credits.mapToCreditsDomainModels()
        } catch (e: Exception) {
            //AHMED_TODO: handle me
            emptyList()
        }
    }

    override suspend fun addMovieRating(movieId: Int, rating: Int) = doInBackground {
        remoteMoviesDataSource.addMovieRating(movieId = movieId, rating = rating)
    }

    override fun getMovieGenreList(): Flow<List<GenreDomainModel>> =
        localMoviesDataSource.getMovieGenreList()
            .map {
                it.mapToGenresDomainModels()
            }
            .flowOnBackground()

    //AHMED_TODO: refactor me
    override suspend fun syncGenres(): Boolean {
        val fetchedGenreList = remoteMoviesDataSource.getMovieGenreList()
        localMoviesDataSource.updateMovieGenreList(fetchedGenreList)
        Timber.e("Syncedd")
        return true
    }
}