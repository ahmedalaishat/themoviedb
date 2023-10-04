package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.toMovieDomainModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.pagerFlowOf
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.connection.model.ConnectionStateDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteAccountDataSource
import com.alaishat.ahmed.themoviedb.di.AppDispatchers
import com.alaishat.ahmed.themoviedb.di.Dispatcher
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import com.alaishat.ahmed.themoviedb.domain.repository.BackgroundExecutor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class AccountRepositoryImpl @Inject constructor(
    private val remoteAccountDataSource: RemoteAccountDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val connectionDataSource: ConnectionDataSource,
    @Dispatcher(AppDispatchers.IO) override val ioDispatcher: CoroutineDispatcher,
) : AccountRepository, BackgroundExecutor {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getWatchListPagingFlow(): Flow<PagingData<MovieDomainModel>> =
        connectionDataSource.observeIsConnected().flatMapLatest {
            if (it is ConnectionStateDataModel.Connected) {
                remoteAccountDataSource.getWatchlistPagingFlow(
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
            remoteAccountDataSource.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist)
        } catch (e: Exception) {
            // in case the request fails undo toggle cached movie
            localMoviesDataSource.cacheMovieWatchlistStatus(movieId = movieId, watchlist = !watchlist)
        }
    }
}