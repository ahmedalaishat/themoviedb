package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import com.alaishat.ahmed.themoviedb.data.mapper.toDomainException
import com.alaishat.ahmed.themoviedb.data.model.ConnectionStateDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.data.model.mapToCreditsDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToGenresDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.model.toMovieDomainModel
import com.alaishat.ahmed.themoviedb.data.model.toReviewDomainModel
import com.alaishat.ahmed.themoviedb.data.source.connection.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.data.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.data.source.remote.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.data.source.remote.paging.pagerFlowOf
import com.alaishat.ahmed.themoviedb.domain.achitecture.util.mapData
import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
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

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class MoviesRepositoryImpl(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val connectionDataSource: ConnectionDataSource,
    private val movieDetailsToDomainResolver: MovieDetailsToDomainResolver,
    override val ioDispatcher: CoroutineDispatcher,
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
        }.retryWhen { _, _ ->
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
                remoteMoviesDataSource.getSearchMoviePagingFlow(
                    query = query,
                    pageCachingHandler = { _, pageData ->
                        localMoviesDataSource.cacheMovies(
                            movies = pageData,
                        )
                    }
                )
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMovieReviewsPagingFlow(movieId: Int): Flow<PagingData<ReviewDomainModel>> {
        val flow = connectionDataSource.observeIsConnected().flatMapLatest {
            if (it is ConnectionStateDataModel.Connected) {
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

        }

        return flow
            .mapData(ReviewDataModel::toReviewDomainModel)
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

    override suspend fun addMovieRating(movieId: Int, rating: Int): Boolean = doInBackground {
        return@doInBackground try {
            remoteMoviesDataSource.addMovieRating(movieId = movieId, rating = rating)
            true
        } catch (ignored: Exception) {
            false
        }
    }

    override fun getMovieGenreList(): Flow<GenresDomainModel> = connectionDataSource.observeIsConnected().map {
        if (it is ConnectionStateDataModel.Connected) {
            val fetchedGenreList = remoteMoviesDataSource.getMovieGenreList()
            localMoviesDataSource.updateMovieGenreList(fetchedGenreList)
            GenresDomainModel.Success(fetchedGenreList.mapToGenresDomainModels())
        } else {
            val cached = localMoviesDataSource.getMovieGenreList()
            if (cached.isEmpty()) GenresDomainModel.NoCache
            else GenresDomainModel.Success(cached.mapToGenresDomainModels())
        }
    }
        .retryWhen { _, _ ->
            emit(GenresDomainModel.NoCache)
            delay(1000)
            true
        }
        .flowOnBackground()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getWatchListPagingFlow(): Flow<PagingData<MovieDomainModel>> =
        connectionDataSource.observeIsConnected().flatMapLatest {
            if (it is ConnectionStateDataModel.Connected) {
                remoteMoviesDataSource.getWatchlistPagingFlow(
                    pageCachingHandler =
                    { page, movies ->
                        localMoviesDataSource.cacheMovieList(
                            deleteCached = page == 1,
                            movieListTypeDataModel = MovieListTypeDataModel.WATCHLIST,
                            movies = movies,
                        )
                    }
                )
            } else {
                pagerFlowOf(
                    data = localMoviesDataSource.getCachedMovieList(
                        movieListTypeDataModel = MovieListTypeDataModel.WATCHLIST
                    )
                )
            }
        }
            .mapData(MovieDataModel::toMovieDomainModel)
            .flowOnBackground()

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) = doInBackground {
        localMoviesDataSource.cacheMovieWatchlistStatus(movieId = movieId, watchlist = watchlist)
        try {
            remoteMoviesDataSource.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist)
        } catch (e: Exception) {
            // in case the request fails undo toggle cached movie
            localMoviesDataSource.cacheMovieWatchlistStatus(movieId = movieId, watchlist = !watchlist)
        }
    }
}