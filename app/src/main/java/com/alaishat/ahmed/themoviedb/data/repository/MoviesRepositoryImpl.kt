package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import com.alaishat.ahmed.themoviedb.data.mapper.toDomainException
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.data.model.mapToCreditsDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToGenresDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.model.toMovieDomainModel
import com.alaishat.ahmed.themoviedb.data.model.toReviewDomainModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.pagerFlowOf
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.connection.model.ConnectionStateDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.network.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.di.AppDispatchers
import com.alaishat.ahmed.themoviedb.di.Dispatcher
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.BackgroundExecutor
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
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

    override fun getTopFiveMovies(): Flow<List<MovieDomainModel>> = connectionDataSource.observeIsConnected()
        .map { connected ->
            if (connected is ConnectionStateDataModel.Connected) {
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
            } else {
                localMoviesDataSource.getCachedMovieList(MovieListTypeDataModel.TOP_FIVE).mapToMovies()
            }
        }.retryWhen { cause, _ ->
            delay(1000)
            true
        }
        .flowOnBackground()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMoviesPagingFlowByType(movieListTypeDomainModel: MovieListTypeDomainModel): Flow<PagingData<MovieDomainModel>> {
        val flow = connectionDataSource.observeIsConnected().flatMapLatest {
            if (it is ConnectionStateDataModel.Connected) {
                remoteMoviesDataSource.getCacheableMoviesPagingFlow(
                    movieListTypeDataModel = MovieListTypeDataModel.getByMovieListTypeDomainModel(
                        movieListTypeDomainModel
                    ),
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
            } else {
                localMoviesDataSource.getCachedMoviesPagingFlow(
                    MovieListTypeDataModel.getByMovieListTypeDomainModel(movieListTypeDomainModel)
                )
            }
        }
        return flow.mapData(MovieDataModel::toMovieDomainModel)
            .flowOnBackground()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDomainModel>> =
        connectionDataSource.observeIsConnected().flatMapLatest {
            if (it is ConnectionStateDataModel.Connected) {
                remoteMoviesDataSource.getSearchMoviePagingFlow(query = query)
            } else {
                pagerFlowOf(
                    data = localMoviesDataSource.searchCachedMovieList(query = query)
                )
            }
        }
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
                emit(MovieDetailsDomainModel.Error(cause.toDomainException()))
                delay(1000)
                true
            }
            .flowOnBackground()
    }

    override fun observeWatchlist(movieId: Int): Flow<Boolean> {
        return localMoviesDataSource.observeMovieWatchlistStatus(movieId = movieId)
    }

    override fun getMovieReviewsPagingFlow(movieId: Int): Flow<PagingData<ReviewDomainModel>> {
        val flow = if (connectionDataSource.getConnectionState() is ConnectionStateDataModel.Connected) {
            remoteMoviesDataSource.getMovieReviewsPagingFlow(
                movieId = movieId,
                pageCachingHandler = { _, reviews ->
                    localMoviesDataSource.cacheMovieReviews(movieId = movieId, reviews = reviews)
                }
            )
        } else {
            localMoviesDataSource.getCachedReviewsPagingFlow(
                movieId = movieId
            )
        }
        return flow.mapData(ReviewDataModel::toReviewDomainModel)
            .flowOnBackground()
    }

    override fun getMovieCredits(movieId: Int): Flow<CreditsDomainModel> =
        localMoviesDataSource.getCachedMovieCredits(movieId).combine(
            connectionDataSource.observeIsConnected()
        ) { cached, connected ->
            if (cached.isNotEmpty()) {
                CreditsDomainModel.Success(cached.mapToCreditsDomainModels())
            } else {
                if (connected is ConnectionStateDataModel.Connected) {
                    val credits = remoteMoviesDataSource.getMovieCredits(movieId = movieId)
                    localMoviesDataSource.cacheMovieCredits(movieId, credits)
                    CreditsDomainModel.Success(credits.mapToCreditsDomainModels())
                } else {
                    CreditsDomainModel.Disconnected
                }
            }
        }.retryWhen { cause, _ ->
            emit(CreditsDomainModel.Error(cause.toDomainException()))
            delay(1000)
            true
        }
            .flowOnBackground()

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
        return try {
            val fetchedGenreList = remoteMoviesDataSource.getMovieGenreList()
            localMoviesDataSource.updateMovieGenreList(fetchedGenreList)
            Timber.e("Syncedd")
            true
        } catch (e: Exception) {
            false
        }
    }
}