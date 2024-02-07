package com.alaishat.ahmed.themoviedb.data.repository

import com.alaishat.ahmed.themoviedb.data.architecture.getOrNull
import com.alaishat.ahmed.themoviedb.data.architecture.getOrThrow
import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import com.alaishat.ahmed.themoviedb.data.mapper.mapToDomainModels
import com.alaishat.ahmed.themoviedb.data.mapper.mapToMovies
import com.alaishat.ahmed.themoviedb.data.mapper.toDataModel
import com.alaishat.ahmed.themoviedb.data.mapper.toDomainException
import com.alaishat.ahmed.themoviedb.data.mapper.toDomainModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.data.model.mapToCreditsDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToReviewsDomainModels
import com.alaishat.ahmed.themoviedb.data.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.data.source.remote.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel.Connected
import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.BackgroundExecutor
import com.alaishat.ahmed.themoviedb.domain.repository.ConnectionRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class MoviesRepositoryImpl(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val movieDetailsToDomainResolver: MovieDetailsToDomainResolver,
    private val connectionRepository: ConnectionRepository,
    override val ioDispatcher: CoroutineDispatcher,
    private val coroutineScope: CoroutineScope,
) : MoviesRepository, BackgroundExecutor {
    private val genres = MutableStateFlow<GenresDomainModel?>(null)

    init {
        loadGenres()
    }

    private fun loadGenres() {
        localMoviesDataSource.getMovieGenreList()
            .takeIf { it.isNotEmpty() }
            ?.let { cached ->
                genres.update {
                    GenresDomainModel.Success(cached.mapToDomainModels())
                }
            }
            ?: observeGenres()
    }

    private fun observeGenres() = coroutineScope.launch {
        connectionRepository.observeConnectionState().collect { connectionStatus ->
            remoteMoviesDataSource
                .takeIf { connectionStatus is Connected }
                ?.getMovieGenreList()
                ?.getOrNull()
                ?.also { fetchedGenreList ->
                    localMoviesDataSource.updateMovieGenreList(fetchedGenreList)
                    genres.update { GenresDomainModel.Success(fetchedGenreList.mapToDomainModels()) }
                    cancel()
                }
                ?: run {
                    genres.update { GenresDomainModel.NoCache }
                }
        }
    }

    override fun getTopFiveMovies(): Flow<List<MovieDomainModel>> = flow {
        val movies = try {
            remoteMoviesDataSource.getMoviesPage(
                movieListTypeDataModel = MovieListTypeDataModel.TOP_FIVE,
                page = 1,
            ).take(5).also { fetchedMovies ->
                localMoviesDataSource.cacheMovieList(
                    deleteCached = true,
                    movieListTypeDataModel = MovieListTypeDataModel.TOP_FIVE,
                    movies = fetchedMovies,
                )
            }
        } catch (e: Exception) {
            localMoviesDataSource.getCachedMovieList(MovieListTypeDataModel.TOP_FIVE)
        }
        emit(movies.mapToMovies())
    }
        .flowOnBackground()

    override suspend fun getMoviesPageByType(
        movieListTypeDomainModel: MovieListTypeDomainModel,
        page: Int,
    ): List<MovieDomainModel> = doInBackground {
        suspend fun remoteMovies() = remoteMoviesDataSource.getMoviesPage(
            movieListTypeDataModel = movieListTypeDomainModel.toDataModel(),
            page = page,
        )

        fun cachedMovies() = localMoviesDataSource.getCachedMoviesPagingFlow(
            movieListType = movieListTypeDomainModel.toDataModel(),
            page = page,
        )

        fun cacheMovies(movies: List<MovieDataModel>) = localMoviesDataSource.cacheMovieList(
            deleteCached = page == 1,
            movieListTypeDataModel = movieListTypeDomainModel.toDataModel(),
            movies = movies,
        )

        val movies = if (connectionRepository.getConnectionState() is Connected) {
            try {
                val remote = remoteMovies() // fetch movies
                cacheMovies(remote)
                remote
            } catch (e: Exception) {
                cachedMovies().ifEmpty { throw e }
            }
        } else {
            cachedMovies()
        }

        movies.map { it.toDomainModel() }
    }

    override suspend fun getSearchMoviePage(query: String, page: Int): List<MovieDomainModel> = doInBackground {
        val connectionState = connectionRepository.getConnectionState()

        val movies = if (connectionState is Connected)
            remoteMoviesDataSource.fetchSearchMoviePage(
                query = query,
                page = page,
            )
                .getOrThrow()
                .also { fetchedMovies ->
                    localMoviesDataSource.cacheMovies(movies = fetchedMovies)
                }
        else localMoviesDataSource.searchCachedMoviePage(
            query = query,
            page = page
        )
        movies.mapToMovies()
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsDomainModel> = flow {
        val connectionStatus = connectionRepository.getConnectionState()
        val movie = movieDetailsToDomainResolver.toDomain(
            connectionState = connectionStatus,
            remoteMovieProvider = {
                coroutineScope {
                    val statusReq = async { remoteMoviesDataSource.getMovieAccountStatus(movieId = movieId) }
                    val movieDetailsReq = async { remoteMoviesDataSource.getMovieDetails(movieId = movieId) }

                    val movieDetails = movieDetailsReq.await().getOrNull() ?: return@coroutineScope null
                    localMoviesDataSource.cacheMovieDetails(movieDetails)

                    val status = statusReq.await().getOrNull() ?: return@coroutineScope null
                    localMoviesDataSource.cacheMovieWatchlistStatus(
                        movieId = movieId,
                        watchlist = status.watchlist
                    )

                    movieDetails
                }
            },
            localMovieProvider = {
                delay(200)
                localMoviesDataSource.getCachedMovieDetails(movieId = movieId)
            }
        )
        emit(movie)
    }
        .flowOnBackground()

    override fun isWatchlist(movieId: Int): Flow<Boolean> {
        return localMoviesDataSource.observeMovieWatchlistStatus(movieId = movieId)
    }

    override suspend fun getMovieReviewsPage(movieId: Int, page: Int): List<ReviewDomainModel> = doInBackground {
        val connectionStatus = connectionRepository.getConnectionState()
        val movies = if (connectionStatus is Connected) {
            remoteMoviesDataSource.getMovieReviewsPage(
                movieId = movieId,
                page = page,
            )
                .getOrThrow()
                .also { reviews ->
                    localMoviesDataSource.cacheMovieReviews(movieId = movieId, reviews = reviews)
                }
        } else {
            localMoviesDataSource.getCachedReviewsPage(
                movieId = movieId,
                page = page,
            )
        }
        movies.mapToReviewsDomainModels()
    }

    override fun getMovieCredits(movieId: Int): Flow<CreditsDomainModel> =
        localMoviesDataSource.getCachedMovieCredits(movieId).combine(
            connectionRepository.observeConnectionState()
        ) { cached, connected ->
            if (cached.isNotEmpty()) {
                CreditsDomainModel.Success(cached.mapToCreditsDomainModels())
            } else {
                if (connected is Connected) {
                    val credits = remoteMoviesDataSource.getMovieCredits(movieId = movieId).getOrThrow()
                    localMoviesDataSource.cacheMovieCredits(movieId, credits)
                    CreditsDomainModel.Success(credits.mapToCreditsDomainModels())
                } else {
                    CreditsDomainModel.NoCache
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

    override fun getMovieGenreList(): Flow<GenresDomainModel> = genres.filterNotNull()

    override suspend fun getWatchListPage(page: Int): List<MovieDomainModel> = doInBackground {
        val connectionStatus = connectionRepository.getConnectionState()
        val movies = if (connectionStatus is Connected)
            remoteMoviesDataSource.getWatchlistPage(page)
                .getOrThrow()
                .also { fetchedMovies ->
                    localMoviesDataSource.cacheMovieList(
                        deleteCached = page == 1,
                        movieListTypeDataModel = MovieListTypeDataModel.WATCHLIST,
                        movies = fetchedMovies,
                    )
                }
        else localMoviesDataSource.getCachedWatchlistPage(page = page)
        movies.mapToMovies()
    }

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) = doInBackground {
        localMoviesDataSource.cacheMovieWatchlistStatus(movieId = movieId, watchlist = watchlist)
        try {
            remoteMoviesDataSource.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist).getOrThrow()
        } catch (e: Exception) {
            // in case the request fails undo toggle cached movie
            localMoviesDataSource.cacheMovieWatchlistStatus(movieId = movieId, watchlist = !watchlist)
        }
    }
}